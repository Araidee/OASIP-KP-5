<script setup>
import { ref, computed, defineAsyncComponent } from "vue";
defineEmits(["addUser"]);
const props = defineProps({
  users: {
    type: Array,
    default: [],
  }
});

const name = ref("");
const email = ref("");
const role = ref("");
const nameMax = 100
const successInput = 'input input-bordered input-success w-full inputwdt'
const errorInput = 'input input-bordered input-error w-full inputwdt'
// const eventDuration = computed((id)=> {

// })

const clearInput = () => {
  name.value = "";
  email.value = "";
  role.value = "";
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
        <h1 class="text-2xl font-bold center">Register</h1>
      </div>
      <div class="center font-bold">
        Name: <span style="color: red;">*</span><br>
        <input
          type="text"
          v-model="name"
          placeholder="Type Name here"
          :class="name=='' ? errorInput:successInput"
          maxlength="100"
          required
        />
        <p class="text-sm ">{{name.length}}/{{nameMax}}</p>
      </div>
      <div class="center font-weight-bold">
        Email: <span style="color: red;">*</span><br>
        <input
          type="text"
          id="email"
          v-model="email"
          placeholder="Type Email here"
          :class="email=='' ? errorInput:successInput"
          required
        /><br />
      </div>
        <div class-="card-actions justify-end">
          <button
            class="btn btn-primary btn-success "
            @click="
              emailValidate()
                ? $emit('addUser', {
                    name: name,
                    email: email,
                    role: role 
                  })
                : '';
              clearInput();
            "
          >
            Register
          </button>
      </div>
    </div>
  </div>
  
</template>

<style scoped>
.position1{
   position: relative;
   left: 15%;
}

.textareacss{
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