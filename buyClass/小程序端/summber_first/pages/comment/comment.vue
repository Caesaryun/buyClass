<template>
	<view>
	 <view class="comment-container">
	 	
	 	<view class="comment-info" v-for="one in list" :key="one">
	 		<image  class="user-header" :src="one.photo" mode="widthFix"></image>
	 	    <view class="comment-context">
	 			<view class="line-1">
	 			<text>{{one.nick_name}} </text>
	 			<uni-rate :value="one.rating" size="18" style="margin-top: 18rpx;"></uni-rate>
	 			
	 			</view>
	 			<view class="line-2">{{one.comment}}</view>
	 		</view>
	 	</view>
	 </view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				id:null,
				comment:[],
				page:1,
				length:5,
				flag:false,
				list:[]
			}
		},
		methods: {
			searchByPage:function(that,bool){
				
				if(that.keyword!=null && that.keyword!="null" &&  that.keyword.replace(" "," ")!=="")
				{
					param.keyword=that.keyword
				}
				uni.request({
					url:that.url.searchCommentPage,
					method:'POST',
					header:{
						"token":uni.getStorageSync("token")
					},
						
					data:{
						"curriculumId":that.id,
						"page":that.page,
						"length":that.length,
						"wang":"tom"
					},
					
					success:function(resp){
						
						let list=resp.data.result
						//页数减 1
						if(bool && list.length==0){
							that.page=that.page-1
							that.flag=true
							return 
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
		onLoad:function(options) {
			let curriculumId=options.id
			
			this.id=curriculumId
			
			console.log("课程id:")
			console.log(this.id)
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
	@import url("comment.less");

</style>
