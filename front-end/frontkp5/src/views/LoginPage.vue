<script setup>
    import { ref, onBeforeMount } from "vue";
    import Login from "../components/Login.vue";
    
    const users = ref([]);
    const isLogin = ref(false)
    onBeforeMount(async () => {
      await getUsers();
    });
    //GET
    const getUsers = async () => {
      // const res = await fetch("http://202.44.9.103:8080/kp5/api/users");
      const res = await fetch("https://intproj21.sit.kmutt.ac.th/kp5/api/users")
      // const res = await fetch(`${import.meta.env.LOCAL_URL}/api/events`)
      if (res.status === 200) {
        users.value = await res.json();
      } else console.log("Error, cannot get data");
    };
    
    //POST
    const login = async (user) => {
        // const res = await fetch("http://202.44.9.103:8080/kp5/api/users", {
         const res = await fetch(`https://intproj21.sit.kmutt.ac.th/kp5/api/match`, {
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
      } else if (res.status === 404){
        alert("Email doesn't exist")
        console.log("email doesn't exist");
      }else if (res.status === 401){
        alert("Password not match")
        console.log("password not match");
      }
      console.log("error, something went wrong");
    };
    </script>
    
    <template>
      <div>
        <Login
          :users="users"
          @login="login"
          />
      </div>
    </template>