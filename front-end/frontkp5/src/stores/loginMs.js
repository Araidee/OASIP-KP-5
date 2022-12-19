import { defineStore, acceptHMRUpdate } from "pinia";
import { cookieData } from "./cookieData.js";
import { useRouter } from "vue-router";
import { loginState } from "./loginState.js";
import { UserAgentApplication } from "msal";

export const msData = defineStore("msDataState", () => {
  const cookie = cookieData();
  const loginData = loginState();
  const router = useRouter();
  //copy from azure
  const msalConfig = {
    auth: {
      clientId: "a2ee6adb-3401-419e-9372-9c541b471a4b",
      authority:
        "https://login.microsoftonline.com/6f4432dc-20d2-441d-b1db-ac3380ba633d",
      redirectURI: "http://localhost:3000/",
    },
    cache: {
      cacheLocation: "localStorage", // This configures where your cache will be stored
      storeAuthStateInCookie: true,
      popUp: true, // Set this to "true" if you are having issues on IE11 or Edge
    },
  };

  var requestObj = {
    scopes: ["user.read"],
  };

  var myMSALObj = new UserAgentApplication(msalConfig);

  async function loginMs() {
    console.log("test");
    var authResult = await myMSALObj.loginPopup(requestObj);
    const cookieMs = cookie.getCookie(
      "msal.a2ee6adb-3401-419e-9372-9c541b471a4b.idtoken"
    );
    loginData.setLogin();
    loginData.setLoginMsUser(authResult.account);

    const url = "https://intproj21.sit.kmutt.ac.th/kp5/api"
    // const url = "http://202.44.9.103:8080/kp5/api";
    const res = await fetch(`${url}/jwt/loginms`, {
      method: "POST",
      body: JSON.stringify(cookieMs),
    });
    if (res.status === 201) {
      const token = await res.json();
      cookie.setCookie("token", token.token, 7);
    } else if (res.status === 403) {
      let response = await res.json();
      if (
        response.message
          .toLowerCase()
          .match(
            "cannot refresh token. need to login again".toLowerCase() ||
              response.message
                .toLowerCase()
                .match("Claims == null, Can't Refresh".toLowerCase())
          )
      ) {
        cookie.eraseCookie("token");
        cookie.eraseCookie("refreshToken");
        alert("Please login again");
      }
    }

    alert("success");
    router.push({ path: "/", replace: true });
    return authResult.account;
  }

  var getAccount = async () => {
    var account = await myMSALObj.getAccount();
    return account;
  };

  var logoffMs = () => {
    myMSALObj.logout();
  };

  return { loginMs, logoffMs, getAccount };
});

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(msData, import.meta.hot));
}
