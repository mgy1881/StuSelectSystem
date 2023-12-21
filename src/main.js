import './assets/main.scss'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'


import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from '@/router'
import App from './App.vue'
import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'
import locale from 'element-plus/dist/locale/zh-cn.js'

const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
const pinia = createPinia();
const persist = createPersistedState();
pinia.use(persist)
app.use(pinia)
app.use(router)
app.use(ElementPlus, { locale });
app.mount('#app')