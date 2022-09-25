<script setup>
    import { ref } from "vue";
    import Login from "../components/Login.vue";
  
    let isLogin = ref(false)
    
    //POST
    const login = async (user) => {
        // const res = await fetch("http://202.44.9.103:8080/kp5/api/login", {
         const res = await fetch(`https://intproj21.sit.kmutt.ac.th/kp5/api/login`, {
        method: "POST",
        headers: {
          "content-type": "application/json",
        },
        body: JSON.stringify(user),
      });
      if (res.status === 200) {
        isLogin = true;
        alert("Login successfully!")
        console.log("login successfully");
        console.log(isLogin)
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