<template>
	<view>
		<view class="cu-timeline">
			<view v-if="qqb==null?true:false" class="cu-item cur cuIcon-moneybag">
				<view class="content bg-red shadow-blur">
					<text></text> {{msg}}
				</view>
			</view>
			<view  v-if="qqb==null?false:true" v-for="(item,index) in qqb" :key="index" class="cu-item cur cuIcon-moneybagfill">
				<view class="content bg-blue shadow-blur">
					<text>{{item.addtime}}</text> 【{{item.type}}】球球币+{{item.number}}！
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				qqb:null,
				user:null,
				msg:'还没有收益！赶紧去看视频获取球球币吧！'
			}
		},
		onLoad() {
			let t = this
			uni.getStorage({
				key:"user",success(e){
					t.user=e.data//这就是你想要取的
					if(!t.user){
						uni.showModal({
						    title: '提示',
						    content: '未登录或已过期请登录后再试！',
							cancelText:'不了',
							confirmText:'去登陆',
						    success: function (res) {
						        if (res.confirm) {
									uni.navigateTo({url:'../login/login'});
						        } else if (res.cancel) {
									uni.switchTab({
										url:'../movies/movies'
									})
						        }
						    }
						});
					}
				}
			});
			this.getqqb();
		},
		methods:{
			getqqb(){
				let t = this;
				uni.request({
					url:getApp().globalData.zuul+'users/user/addqqb',
					method:'POST',
					header:{'Content-Type': 'application/x-www-form-urlencoded',
							value:t.user.sessionid,key:t.user.username},
					data:{
						userid:t.user.id
					},
					success(data) {
						if(data.data.status){
							t.qqb=data.data.data;
							t.qqb.reverse();
						}else{
							t.msg='呃..未获取到记录'
						}
					},
					fail() {
						t.msg='获取记录出错了'
					}
				})
			}
		}
	}
</script>

<style>
</style>
