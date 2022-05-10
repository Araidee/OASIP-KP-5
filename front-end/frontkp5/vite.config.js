import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  base: 'http://intproj21.sit.kmutt.ac.th/kp5/',
  // ,
  // server: {
  //   proxy: {
  //     '/api' : {
  //       target: 'http://10.4.56.88:8080/',
  //       changeOrigin: true,
  //       rewrite: (path) => path.replace(/^\/api/, '')
  //     }
  //   }
  // }
  //  proxy: {
  //   '/api': {
  //        target: 'https://localhost:44305',
  //        changeOrigin: true,
  //        secure: false,      
  //        ws: true,
  //    }
    
})
