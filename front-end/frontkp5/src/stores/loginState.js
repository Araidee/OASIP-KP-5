import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref } from 'vue'
export const loginState = defineStore('loginState', () => {
        let isLogin = ref(false)
        let loginUser = ref({name: 'Guest', role: 'Guest', email: ''})
        function setLogin() {
            isLogin.value = !isLogin.value
        }
        function setLoginUser(user) {
            loginUser.value = user
            loginUser.value.email = user.sub
        }
        function clearLoginUser() {
            loginUser.value = {name: 'Guest', role: 'Guest'}
        }
        return { isLogin, loginUser, setLogin, setLoginUser, clearLoginUser }
    })
    
    
    if (import.meta.hot) {
        import.meta.hot.accept(acceptHMRUpdate(loginState, import.meta.hot))
    }