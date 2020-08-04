<template>
	<view>
		<view class="search-container">
			<image class="logo" mode="widthFix" src="../../static/img/logo-small.jpg"></image>
		   <view class="search-bar">
			<icon type="search" size="16" class="search-icon"></icon>
		    <input v-model="keyword" confirm-type="search" @confirm="toCurriculumList()" type="text" placeholder="请输入关键字"  class="search"/>
		   </view>
		 </view>
		 <swiper  class="swiper-container" :autoplay="true" :interval="5000" :duration="1000" :indicator-dots="false" >
		 <swiper-item  v-for="one in topList" :key="one.id" @tap="toCurriculum(one.id)">
			 <image class="swiper-image" :src="one.banner" mode="widthFix"></image>
		 </swiper-item>
		 </swiper>
		 
		 <view class="hot-container">
			 <view class="title" >热门课程</view>
			 <view class="hot-content" @tap="toCurriculum(hot.id)">
				 <view class="info">
					<view class="curriculum-name">{{hot.name}}</view> 
				    <view class="curriculum-desc">{{hot.suggestion}}</view>
					<view class="teacher">NEXT学院{{hot.title}} {{hot.nick_name}}</view>
				 </view>
				 <image :src="hot.icon" mode="widthFix" class="type-icon"></image>
			 </view>
		 </view>
		 
		 <view class="refined-container">
		 <view class="title" >精制课程</view>
		 <view class="tab-container">
			<view :class="tab_1" @tap="changeTab(1)">小白入门</view>
			<view :class="tab_2" @tap="changeTab(2)">基础进阶</view>
			<view :class="tab_3" @tap="changeTab(3)">专项突破</view>
		 </view>
		 <view class="tab-page-container">
			 <view :class="page_1" >
				 <view class="refined-content" v-for="one in list_1" :key="one" @tap="toCurriculum(one.id)">
				 				 <view class="info">
				 					<view class="curriculum-name">{{one.name}}</view> 
				 				    <view class="curriculum-desc">学习周期：{{one.learning_cycle}}</view>
				 					<view class="curriculum-desc">{{one.tag}}</view>
									<view class="teacher">NEXT学院{{one.title}} {{one.nick_name}}</view>
				 				 </view>
				 				 <image class="type-icon" :src="one.icon" mode="widthFix"></image>
				 </view>
				  <button class="btn" @tap="toAllList('入门')">全部课程</button>
			 </view>
			 <view :class="page_2" >
				 <view class="refined-content" v-for="one in list_2" :key="one" @tap="toCurriculum(one.id)">
				 				 <view class="info">
				 					<view class="curriculum-name">{{one.name}}</view> 
				 				    <view class="curriculum-desc">学习周期：{{one.learning_cycle}}</view>
				 					<view class="curriculum-desc">{{one.tag}}</view>
									<view class="teacher">NEXT学院{{one.title}} {{one.nick_name}}</view>
				 				 </view>
				 				 <image class="type-icon" :src="one.icon" mode="widthFix"></image>
				 </view>
				  <button class="btn" @tap="toAllList('简单')">全部课程</button>
				 </view>
			 <view :class="page_3" >
				 <view class="refined-content" v-for="one in list_3" :key="one" @tap="toCurriculum(one.id)">
				 				 <view class="info">
				 					<view class="curriculum-name">{{one.name}}</view> 
				 				    <view class="curriculum-desc">{{one.learning_cycle}}</view>
				 					<view class="curriculum-desc">{{one.tag}}</view>
									<view class="teacher">NEXT学院{{one.title}} {{one.nick_name}}</view>
				 				 </view>
				 				 <image class="type-icon" :src="one.icon" mode="widthFix"></image>
				 </view>
				 <button class="btn" @tap="toAllList('中等')">全部课程</button>
				</view> 
				
		 </view>
		 </view>
		 
	</view>
</template>

<script>
	export default {
		data() {
			return {
				"topList":[],
				"hot":[],
				"list_1":[],
				"list_2":[],
				"list_3":[],
				tab_1:"tab active",
				tab_2:"tab",
				tab_3:"tab",
				page_1:"page active",
				page_2:"page",
				page_3:"page",
				keyword:null
			}
		},
		methods: {
			toCurriculum:function(id){
				uni.navigateTo({
					url:'../curriculum/curriculum?id='+id
				})
			},
			changeTab:function(n){
				let that = this
				if(n==1)
				{
					that.tab_1="tab active"
					that.tab_2="tab"
					that.tab_3="tab"
					that.page_1="page active"
					that.page_2="page"
					that.page_3="page"
				}
				else if(n==2)
				{
					that.tab_1="tab "
					that.tab_2="tab active"
					that.tab_3="tab"
					that.page_1="page "
					that.page_2="page active"
					that.page_3="page"
				}
				else if(n==3)
				{
					that.tab_1="tab "
					that.tab_2="tab"
					that.tab_3="tab active"
					that.page_1="page "
					that.page_2="page"
					that.page_3="page active"
				}
				
				},
				toAllList:function(param){
					uni.navigateTo({
						url:'../curriculum-list/curriculum-list?difficulty='+param
					})
				},
				toCurriculumList:function(){
					let that=this
					uni.navigateTo({
						url:'../curriculum-list/curriculum-list?keyword='+that.keyword
					})
				}
		},
		onShow:function(){
			let that = this
			uni.request({
				url:that.url.searchTop,
				method:"GET",
				header:{  //上传令牌，进行访问
					"token":uni.getStorageSync("token")
				},
				success:function(resp){
					// console.log("result")
					 //console.log(resp.data.result)
					let list = resp.data.result
					for(let one of list)
					{
						one.banner=that.url.img+"/"+one.banner
					}
					that.topList=list
					
				}
			})
			uni.request({
				url:that.url.searchHot,
				method:"GET",
				header:{
					"token":uni.getStorageSync("token")
				},
				success:function(resp){
					// console.log(resp)
					let result=resp.data.result
					result.icon=that.url.img+"/"+result.icon
					that.hot=result
				}
			})
			
		   uni.request({
		   	url:that.url.searchRefined,
			method:"GET",
			header:{
				"token":uni.getStorageSync("token")
				
			},
			success:function(resp){
				console.log(resp.data)
				let list_1 = that.list_1=resp.data.list_1
				for(let one of list_1)
				{
					one.icon=that.url.img+"/"+one.icon
					one.tag=JSON.parse(one.tag).join("  ·  ")
				}
				that.list_1=list_1;
				
				let list_2=that.list_2=resp.data.list_2
				for(let one of list_2)
				{
					one.icon=that.url.img+"/"+one.icon
					one.tag=JSON.parse(one.tag).join("  · ")
				}
				that.list_2=list_2;
				
				let list_3=that.list_3=resp.data.list_3
				for(let one of list_3)
				{
					one.icon=that.url.img+"/"+one.icon
					one.tag=JSON.parse(one.tag).join("  ·  ")
				}
				that.list_3=list_3;
				}
		   })
		
		}
	}
</script>

<style lang="less">
        @import url("index.less");
		
</style>
