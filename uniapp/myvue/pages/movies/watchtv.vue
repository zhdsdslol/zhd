<template>
	<view>
		<view v-if="movie==null?false:true">
			<video ref='myvideo' style="width: 100%;" @play="isplay=true" @pause="isplay=false" :src="qqyun+movie.url" ></video>
			<scroll-view scroll-x class="bg-white nav text-center">
				<view class="cu-item" :class="tar==1?'text-blue cur':''"   @tap="tabSelect(1)" >
					简介
				</view>
				<view class="cu-item" :class="tar==2?'text-blue cur':''"  @tap="tabSelect(2)" >
					评论({{movie.talk}})
				</view>
			</scroll-view>
			<view v-show="tar==1" >
				<view class="cu-form-group margin-top" style="margin-top: 0px;">
						<textarea  maxlength="-1" disabled="true"  :placeholder="'发布:'+movie.username+'\n简介:'+movie.title"></textarea>
						<image :src="zan?'../../static/icon/zan.ico':'../../static/icon/dianzan.ico'" style="width: 20px;height: 20px;" @click="dianzan">点赞:{{movie.dianzan}}</image>
				</view>
			</view>
			<view v-show="tar==2">
				<view class="cu-timeline">
					<view class="cu-time">13-14</view>
					<view class="cu-item">
						<view class="content">
							<view class="cu-capsule radius">
								<view class="cu-tag bg-cyan">上午</view>
								<view class="cu-tag line-cyan">5:20</view>
							</view>
							<view class="margin-top">评论功能还没开发哦！</view>
						</view>
					</view>
				</view>
				<view class="cu-bar foot input" >
					<view class="action">
						<text class="cuIcon-sound text-grey"></text>
					</view>
					<input class="solid-bottom" :adjust-position="false" :focus="false" maxlength="300" cursor-spacing="10"
					 ></input>
					<view class="action">
						<text class="cuIcon-emojifill text-grey"></text>
					</view>
					<button class="cu-btn bg-green shadow">发送</button>
				</view>
			</view>
		</view>
		<view v-if="movie==null?true:false" class="text-center">
			<text >{{msg}}</text>
		</view>
	</view>
	
</template>

<script>
	export default {
		data(){
			return{
				movie:null,
				qqyun:getApp().globalData.qqyun,
				msg:'视频不见了！？',
				watchtime:0,
				isplay:false,
				user:null,
				TabCur: 0,
				scrollLeft: 0,
				tar:1,
				zan:true
			}
		},
		onLoad(data) {
			let t = this
			uni.getStorage({
				key:'user',success(e) {
					t.user= e.data
				}
			})
			this.getmovie(data.id);
			uni.showToast({
				duration:2000,
				title:"观看5秒钟可获得1球球币哦！",
				icon:'none'
			});
			this.havedianzan(data.id);
			setInterval(this.getqqb,1000);
		},
		methods:{
			havedianzan(id){
				let t = this;
				uni.request({
					url:getApp().globalData.zuul+'movies/movie/havedianzan',
					method:'POST',
					header:{'Content-Type': 'application/x-www-form-urlencoded',
									'key':t.user.username,'value':t.user.sessionid
					},
					data:{
						movieid:id,
						userid:t.user.id,
					},
					success(data) {
						if(data.data.status){
							t.zan=false;
						}
					}
				})
			},
			dianzan(){
				let t = this;
				if(!t.zan){
					uni.showToast({
						icon:'none',
						title:'点过赞了',
						duration:2000
					})
				}else{
					uni.request({
						url:getApp().globalData.zuul+'movies/movie/dianzan',
						method:'POST',
						header:{'Content-Type': 'application/x-www-form-urlencoded',
										'key':t.user.username,'value':t.user.sessionid
						},
						data:{
							movieid:t.movie.id,
							userid:t.user.id,
						},
						success(data){
							if(data.data.status){
								t.zan=false;
								t.movie.dianzan++;
							}else{
								uni.showToast({
									icon:'none',
									title:data.data.msg,
									duration:2000
								})
							}
						}
					})
				}
			},
			tabSelect(e) {
				this.tar = e;
			},
			getqqb(){
				if(this.isplay){
					if(this.watchtime<5){
						this.watchtime++;
						if(this.watchtime>=5){
							uni.showToast({
								duration:1000,
								title:"球球币+1！",
								icon:'none'
							});	
							uni.request({
								url:getApp().globalData.zuul+'users/user/updateqqb',
								method:'POST',
								header:{'Content-Type': 'application/x-www-form-urlencoded',
									'key':this.user.username,'value':this.user.sessionid},
								data:{
									type:'观看收益',
									number:1,
									userid:this.user.id
								},
								success(data) {
									if(!data.data.status){
										uni.showToast({
											duration:2000,
											title:'无法获取球球币：'+data.data.msg,
											icon:'none'
										});
									}
								}
							})
						}
					}else{return;}
				}
			},
			getmovie(id){
				let self = this;
				uni.request({
					url:getApp().globalData.zuul+"movies/movie/getmovie",
					method:'POST',
					header:{'Content-Type': 'application/x-www-form-urlencoded'},
					data:{
						id:id
					},
					success(data) {
						if(data.data.status){
							self.movie=data.data.data;
						}else{
							self.msg=data.data.msg;
						}
					},
					fail(data) {
						self.msg="出错了!";
					}
				})
			}
		}
		
	}
</script>

<style>
</style>
