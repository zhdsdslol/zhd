import Vue from 'vue'
import App from './App'
import qs from 'qs'
Vue.config.productionTip = false
Vue.prototype.$qs = qs
App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
