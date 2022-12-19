<script setup>
import { ref } from "vue";

defineEmits(["login", "loginMs"]);

// const roles = ({
//   Admin: "admin",
//   Lecturer: "lecturer",
//   Student: "student"
// })

const email = ref("");
const password = ref("");

const clearInput = () => {
  email.value = "";
  password.value = "";
};

function emailValidate() {
  let regx =
    /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
  if (email.value.match(regx)) {
    return true;
  } else {
    alert("Sorry! Incorrect Email Address");
    return false;
  }
}
</script>

<template>
  <div>
    <div class="card-compact w-3/4 bg-base-100 shadow-xl position1">
      <div class="card-body items-center">
        <h1 class="text-2xl font-bold center">Sign In</h1>
      </div>
      <div class="center font-weight-bold">
        Email: <span style="color: red">*</span><br />
        <input
          type="text"
          id="email"
          v-model="email"
          class="input input-bordered input-success w-full inputwdt"
          placeholder="Type Email here"
          required
        /><br />
      </div>
      <div class="center font-weight-bold">
        Password: <span style="color: red">*</span><br />
        <input
          type="password"
          v-model="password"
          class="input input-bordered input-success w-full inputwdt"
          placeholder="Type Password here"
          maxlength="100"
          required
        />
      </div>
      <!-- <div class="center font-weight-bold">
        <span
          >Didn't have an account?
          <router-link to="/register" class="underline text-blue-700"
            >SignUp</router-link
          ></span
        >
      </div> -->
      <div class="card-actions center">
        <button
          class="btn btn-primary btn-success hover:bg-green-700"
          @click="
            emailValidate()
              ? $emit('login', {
                  email: email,
                  password: password,
                })
              : '';
            clearInput();
          "
        >
          Login
        </button>
        <button
          class="btn bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
          @click="$emit('loginMs')"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="inline-block fill-current h-4 w-4 mr-2"
            viewBox="0 0 23 23"
          >
            <path fill="#f3f3f3" d="M0 0h23v23H0z" />
            <path fill="#f35325" d="M1 1h10v10H1z" />
            <path fill="#81bc06" d="M12 1h10v10H12z" />
            <path fill="#05a6f0" d="M1 12h10v10H1z" />
            <path fill="#ffba08" d="M12 12h10v10H12z" />
          </svg>
          Login with Microsoft
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.position1 {
  position: relative;
  left: 15%;
}

.textareacss {
  width: 500px;
  height: 180px;
}

.inputwdt {
  width: 500px;
}

.center {
  margin: auto;
  width: 48%;
  padding: 10px;
  font-weight: bold;
}
</style>
