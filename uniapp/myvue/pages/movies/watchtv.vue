<template>
	<view>
		<view v-if="movie==null?false:true">
			<video ref='myvideo' style="width: 100%;" @play="isplay=true" @pause="isplay=false" :src="qqyun+movie.url" ></video>
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
				user:null
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
			setInterval(this.getqqb,1000);
		},
		methods:{
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
								header:{'key':this.user.username,'value':this.user.sessionid},
								data:{
									
								},
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
