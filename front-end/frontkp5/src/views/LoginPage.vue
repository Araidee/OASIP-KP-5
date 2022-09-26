<script setup>
    import { ref } from "vue";
    import VueCookies from "vue-cookies";
    import Login from "../components/Login.vue";

    let token = ref()
    //POST
    const login = async (user) => {
        const res = await fetch("http://localhost:8080/api/jwt/login", {
        // const res = await fetch("http://202.44.9.103:8080/kp5/api/login", {
        // const res = await fetch(`https://intproj21.sit.kmutt.ac.th/kp5/api/login`, {
        method: "POST",
        headers: {
          "content-type": "application/json",
        },
        body: JSON.stringify(user),
      });
      if (res.status === 200) {
        token = await res.json();
        VueCookies.set(Object.keys(token)[0], Object.values(token)[0], 7)
        alert("Login successfully!")
        console.log("login successfully");
      }else if (res.status === 404){
        alert("Email doesn't exist")
        console.log("email doesn't exist");
      }else if (res.status === 401){
        alert("Password not match")
        console.log("password not match");
      }else console.log("error, something went wrong");
    };
    </script>
    
    <template>
      <div>
        <Login
          @login="login"
          />
      </div>
    </template>