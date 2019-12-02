<template>
	<view>
		<view class="cu-item text-blue" v-if="orders==null?true:false">
			<view class="bg-blue shadow-blur content">
				<view class="cu-list menu-avatar radius">
					<view class="cu-item" >
						<view class="cu-avatar round lg">
							<text>无</text>
						</view>
							<text style="color: #000000;">还没购买任何东西哦！去商城看看吧!</text>	
					</view>
				</view>
			</view>
		</view>
			
		<view class="cu-item text-blue" v-if="orders==null?false:true">
			<view class="bg-blue shadow-blur content" v-for="(item,index) in orders" :key="index">
				<view class="cu-list menu-avatar radius" >
					<view class="cu-item" >
						<view class="cu-avatar round lg" :style="[{'background-image':'url('+qqyun+item.pic+')'}]">
						</view>
						<view class="content">
							<view class="text-grey">{{item.name}}
							</view>
							<view class="text-gray text-sm">
								<text class="cuIcon-time text-black">支付时间:{{item.addtime}}</text></view>
						</view>
						<view class="action" style="margin-left: 70px;">
							<view class="text-grey text-xs"><text class="cuIcon-moneybagfill text-red">- {{item.price}}</text></view>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				orders:null,
				user:null,
				qqyun:getApp().globalData.qqyun
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
			this.getorders();
		},
		methods:{
			getorders(){
				let t = this;
				uni.request({
					url:getApp().globalData.zuul+'shops/shop/getorders',
					method:'POST',
					header:{'Content-Type': 'application/x-www-form-urlencoded',
							value:t.user.sessionid,key:t.user.username},
					data:{
						userid:t.user.id
					},
					success(data) {
						if(data.data.status==true){
							t.orders=data.data.data;
							t.orders.reverse();
						}else{
							uni.showToast({
								title:"获取订单失败！",
								icon:'loading',
								duration:2000
							})
						}
					},
					fail() {
						uni.showToast({
								title:"获取订单失败！",
								icon:'loading',
								duration:2000
							})
					}
				})
			}
		}
	}
</script>

<style>
</style>
