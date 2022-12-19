import { defineStore, acceptHMRUpdate } from "pinia";
import { ref } from "vue";
import { cookieData } from "./cookieData";
export const loginState = defineStore("loginState", () => {
  const cookie = cookieData();
  let isLogin = ref(false);
  let loginUser = ref({ name: "Guest", role: "Guest", email: "" });
  function setLogin() {
    isLogin.value = !isLogin.value;
  }
  function setLoginUser(user) {
    loginUser.value = user;
    loginUser.value.email = user.sub;
  }

  function setLoginMsUser(user){
    loginUser.value.name = user.name;
    loginUser.value.role = user.idToken.roles[0];
    loginUser.value.email = user.userName;
  }

  function clearLoginUser() {
    loginUser.value = { name: "Guest", role: "Guest" };
  }
  const checkLogin = async () => {
    cookie.getCookie("token");
    if (cookie.getCookie("token") != "" ) {
      isLogin.value = true;
    } else {
      isLogin.value = false;
    }
  }

  const refreshToken = async () => {
    const url = "http://intproj21.sit.kmutt.ac.th/kp5/api";
    // const url = "http://202.44.9.103:8080/kp5/api";
    // const res = await fetch("http://202.44.9.103:8080/kp5/api/users");
    const res = await fetch(`${url}/jwt/refresh`, {
      method: "GET",
      headers: {
        Authorization: "Bearer " + cookie.getCookie("refreshToken"),
        isRefreshToken: true,
      },
    });
    // const res = await fetch(`${import.meta.env.LOCAL_URL}/api/events`)
    if (res.status === 200) {
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
  };
  
  return { isLogin, loginUser, setLogin, setLoginUser, clearLoginUser, refreshToken, checkLogin, setLoginMsUser };
});

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(loginState, import.meta.hot));
}
