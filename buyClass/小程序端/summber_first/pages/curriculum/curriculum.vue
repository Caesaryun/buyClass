<template>
	<view>
		<view class="title">{{summary.title}}</view>
		<video :src="publicVideo" controls class="public-video"></video>
		<view class="attr-info">
			<image src="../../static/img/icon-1.png" class="attr-image" mode="widthFix"></image>
		    <text class="attr">{{summary.buyers}}</text>
			<image src="../../static/img/icon-2.png" class="attr-image" mode="widthFix"></image>
			<text class="attr">{{summary.rating}}</text>
			<image src="../../static/img/icon-3.png" class="attr-image" mode="widthFix"></image>
			<text class="attr">{{summary.comment}}</text>
			
		</view>
		<view class="price-info">
			当前价格：<text class="current-price">￥{{summary.current_price}}</text>
			 原价：<text class="price">￥{{summary.price}}</text>
		</view>
		<view class="special-info">
			<view class="special-icon">保</view>
			<text class="special-tag">支付保障</text>
			<view class="special-icon">退</view>
			<text class="special-tag">课前随时退</text>
			<view class="special-icon">回</view>
			<text class="special-tag">课后有回放</text>
		</view>
		<view class="teacher-container">
			<view class="sub-title">教师介绍</view>
			<view class="teacher">
				<image :src="teacher.photo" mode="widthFix" class="teacher-header"></image>
			    <view class="teacher-info">
					<view class="line-1">
						<text class="teacher-name">{{teacher.nick_name}}</text>
					    <image src="../../static/img/icon-4.png" mode="widthFix" class="title-icon"></image>
					    <text class="teacher-title">{{teacher.title}}</text>
						
					</view>
					<view class="line-2">
						<text class="teacher-attr-tag">好评：{{teacher.rating}}</text>
						<text class="teacher-attr-tag">课程：{{teacher.curriculums}}</text>
						<text class="teacher-attr-tag">学生：{{teacher.students}}</text>
						
					</view>
				</view>
			</view>
			<view class="teacher-introduction">{{teacher.introduction}}</view>
		</view>
		<view class="document-container">
			<view class="sub-title">授课方式</view>
			<view>
				<text class="document-attr-tag">视频({{summary.video_num}})</text>
				<text class="document-attr-separator">·</text>
				<text class="document-attr-tag">文档({{summary.document_num}})</text>
			</view>
		</view>
		
		<view class="index-container">
			<view class="index-title">课程大纲</view>
		    <view class="chapter-container" v-for="(one,i) in index" :key="one">
				<view class="chapter">{{(i+1)+"."+one.chapter}}</view>
			    <view class="lesson" v-for="section in one.section" :key="section">
					<image src="../../static/img/icon-5.png" mode="widthFix" class="play-icon"></image>
				    <view class="lesson-info">
						<view class="lesson-title">{{section.title}}</view>
					    <view>
							<text class="lession-desc">时长：{{section.minute}}分钟</text>
						    <text class="lession-desc">体积：{{section.size}}MB</text>
						    <!-- <text class="lession-desc finish"  v-if="learning.finish_section.indexOf(section.id)!=-1"> 已学完</text> -->
						     <text class="lession-desc finish" v-if="learning.finish_section.indexOf(section.id)!=-1" >已学完</text> 
						</view>
					</view>
				</view>
			</view>
		
		</view>
		
		<view class="comment-container">
			<view class="comment-title-container">
				<view class="comment-title">
					学员评价<text class="comment-sub-title">({{summary.comment}})</text>
				</view>
				<view class="all" @tap="toallcomments()">
					查看全部
					<image src="../../static/img/icon-7.png" mode="widthFix"></image>
				</view>
			</view>
			<view class="comment-info" v-for="one in comment" :key="one">
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
	import uniRate from '@/components/uni-rate/uni-rate.vue'
	// import sxRate from '@/components/sx-rate/index.vue'
	
	export default {
		components: {uniRate},   
		data() {
			return {
				id:null,
				summary:{},
				publicVideo:null,
				teacher:{},
				index:[],
				comment:[],
				learning:{}
				
				
			}
		},
		methods: {
			toallcomments(){
				uni.navigateTo({
					url:"../comment/comment?id="+this.id
				})
			}
		},
		onShow:function(){
			 
		      let that=this
			  uni.request({
			  	url:that.url.searchCurriculum,
				method:"POST",
				header:{
					"token":uni.getStorageSync("token")
				},
				data:{
					"curriculumId":that.id
				},
				success:function(resp){
				      // console.log(resp.data)
					  // let that = this
					  that.summary=resp.data.summary
					  that.publicVideo=that.url.img+"/"+resp.data.publicVideo
					  let teacher=resp.data.teacher
					  teacher.photo=that.url.img+"/"+teacher.photo
					  that.teacher=teacher
					 // that.index=resp.data.index
					  let index=resp.data.index
					  for(let one of index){
						  one.section=JSON.parse(one.section)
					  }
					  that.index=index
					  that.comment=resp.data.comment
					  
					  let learning = resp.data.learning;
					  
					  console.log("finsh-section")
					  learning.finish_section=JSON.parse(learning.finish_section)
					  console.log(learning.finish_section)
					  
					  that.learning=learning
					  
					  
					  // console.log(that.teacher)
				}
			  })
		},
		onLoad:function(options){
			 let that=this
		     let id = options.id
		     that.id=id
			 
		}
	}
</script>

<style lang="less">
    @import url("curriculum.less");
	
	
	
</style>
