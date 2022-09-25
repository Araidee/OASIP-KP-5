<script setup>
    import { ref } from "vue";
    defineEmits(["login"]);
  
    
    // const roles = ({
    //   Admin: "admin", 
    //   Lecturer: "lecturer", 
    //   Student: "student"
    // })
    const email = ref("");
    const password = ref("")
    
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
            Email: <span style="color: red;">*</span><br>
            <input
              type="text"
              id="email"
              v-model="email"
              placeholder="Type Email here"
              required
            /><br />
          </div>
          <div class="center font-weight-bold">
            Password: <span style="color: red;">*</span><br>
            <input
              type="password"
              v-model="password"
              placeholder="Type Password here"
              maxlength="100"
              required
            />
          </div>
            <div class-="card-actions justify-end">
              <button
                class="btn btn-primary btn-success "
                @click="
                  emailValidate()
                    ? 
                    $emit('login', {
                        email: email,
                        password: password
                      })
                    : '';
                  clearInput();
                "
              >
                Login
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