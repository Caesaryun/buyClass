import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false

let baseUrl = "http://localhost:8080/renren-fast"

Vue.prototype.url={
	"login":baseUrl+"/app/user/login",
	"searchTop":baseUrl+"/app/curriculum/searchTop",
    "img":"http://localhost:80",
	"searchHot":baseUrl+"/app/curriculum/searchHot",
	"searchRefined":baseUrl+"/app/curriculum/searchRefined",
    "searchByPage":baseUrl+"/app/curriculum/searchByPage",
    "searchCurriculum":baseUrl+"/app/curriculum/searchCurriculum",
    "searchCommentPage":baseUrl+"/app/curriculum/searchCommentByPage"
}



App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
