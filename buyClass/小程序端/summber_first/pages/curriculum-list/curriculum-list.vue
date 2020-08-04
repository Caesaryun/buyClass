<template>
	<view>
		<view class="curriculum-content" v-for="one in list" :key="one" @tap="toCurriculum(one.id)">
						 <view class="info">
							<view class="curriculum-name">{{one.name}}</view> 
						    <view class="curriculum-desc">学习周期：{{one.learning_cycle}}</view>
							<view class="curriculum-desc">{{one.tag}}</view>
											<view class="teacher">NEXT学院{{one.title}} {{one.nick_name}}</view>
						 </view>
						 <image class="type-icon" :src="one.icon" mode="widthFix"></image>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				difficulty:null,
				keyword:null,
				page:1,
				length:10,
				list:[],
				flag:false
			}
		},
		methods: {
			toCurriculum:function(id){
				uni.navigateTo({
					url:'../curriculum/curriculum?id='+id
				})
			},
			searchByPage:function(that,bool){
				let param={
					page:that.page,
					length:that.length,
					difficulty:that.difficulty
				}
				if(that.keyword!=null && that.keyword!="null" &&  that.keyword.replace(" "," ")!=="")
				{
					param.keyword=that.keyword
				}
				uni.request({
					url:that.url.searchByPage,
					method:'POST',
					header:{
						"token":uni.getStorageSync("token")
					},
					data:param,
					
					success:function(resp){
						//console.log(resp.data.result)
						let list=resp.data.result
						//页数减 1
						if(bool && list.length==0){
							that.page=that.page-1
							that.flag=true
							return 
						}
						for(let one of list)
						{
							one.icon=that.url.img+"/"+one.icon
							one.tag=JSON.parse(one.tag).join("  ·  ")
						}
						
						that.list=that.list.concat(list)
					    uni.showToast({
					    	title:"加载了"+list.length+"条记录",
							icon:"none"
					    })
					}
				})
			}
		},
		onShow:function(){
			let that = this
			that.page=1
			that.flag=false
			that.list=[]
			that.searchByPage(that,false)
			
		},
		onLoad:function(options){
			let that = this
			if(options.hasOwnProperty("difficulty")){
				that.difficulty=options.difficulty
				console.log(that.difficulty)
			}
			if(options.hasOwnProperty("keyword")){
				that.keyword = options.keyword
			}
		},
		onReachBottom:function(){
			console.log("到底")
			let that=this
				
			if(that.flag==false){
				that.page=that.page+1
			    that.searchByPage(that,true)
			}
			
			
		}
	}
</script>

<style lang="less">
    @import url("curriculum-list.less");
	
</style>
