<template>
	<view>
		<swiper class="screen-swiper round-dot" :indicator-dots="true" :circular="true" :autoplay="true" interval="5000"
		 duration="500">
			<swiper-item v-for="(item,index) in 2" :key="index">
				<image :src="qqyun+'shop'+(index+1)+ '.jpg'" mode="aspectFill"></image>
			</swiper-item>
		</swiper>
		<view class="VerticalBox">
			<scroll-view class="VerticalNav nav" scroll-y scroll-with-animation :scroll-top="verticalNavTop" style="height:calc(100vh - 375upx)">
				<view class="cu-item" :class="index==tabCur?'text-green cur':''" v-for="(item,index) in list" :key="index" @tap="TabSelect"
				 :data-id="index">
					{{list[index]}}
				</view>
			</scroll-view>
			<scroll-view class="VerticalMain" scroll-y scroll-with-animation style="height:calc(100vh - 375upx)"
			 :scroll-into-view="'main-'+mainCur" @scroll="VerticalMain">
				<view class="padding-top padding-lr" v-for="(item,listindex) in list" :key="listindex" :id="'main-'+listindex">
					<view class="cu-bar solid-bottom bg-white">
						<view class="action">
							<text class="cuIcon-title text-green"></text> {{list[listindex]}}</view>
					</view>
					<view class="cu-list menu-avatar" >	
						<view class="cu-item" v-if="item.type==list[listindex]" v-for="(item,index) in shop " :key="index" >
							<view class="cu-avatar round lg"  :style="[{'background-Image':'url(' +qqyun+ item.image + ')' }]"></view>
							<view class="content" >
								<view class="text-grey">{{item.name}}</view>
								<view class="text-gray text-sm flex" v-if="item.type=='非卖品'?false:true" >
									<text class="text-cut">
										<text class=" text-red  margin-right-xs"></text>
										剩余:{{item.number}}
									</text> 
								</view>
							</view>
							<view class="action">
								<view class="text-grey text-xs" style="margin-left: -20px;"><text class="cuIcon-moneybagfill text-red">{{item.price}}</text></view>
								<view class="margin-tb-sm text-center" style="margin-left: -20px;">
									<button class="cu-btn round bg-red shadow" style="height: 20px;width: 60px;" v-if="item.type=='非卖品'?false:true" @click="pay(item)">购买</button>
								</view>
							</view>
						</view>
						
					</view>
					
				</view>
			</scroll-view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: ["零食","熬夜","非卖品"],
				tabCur: 0,
				mainCur: 0,
				verticalNavTop: 0,
				load: true,
				qqyun:getApp().globalData.qqyun,
				user:null,
				shop:null
			};
		},
		onLoad() {
			uni.showLoading({
				title: '加载中...',
				mask: true
			});
		},
		onShow() {
			let t = this;
			uni.getStorage({
				key:'user',success(e) {
					t.user= e.data
				}
			})
			t.findshop();
		},
		onReady() {
			uni.hideLoading()
		},
		methods: {
			pay(item){
				if(this.user==null){
					uni.showModal({
					    title: '提示',
					    content: '请登录后再购买！',
						cancelText:'再看看',
						confirmText:'去登陆',
					    success: function (res) {
					        if (res.confirm) {
								uni.navigateTo({url:'../login/login'});
					        } else if (res.cancel) {
					        }
					    }
					});
				}else{
					let t = this;
					uni.showModal({
						cancelColor:'#ED1C24',
						cancelText:'没钱！',
						confirmColor:'#4CD964',
						confirmText:'不差钱！买！',
						title:'提示',
						content:'确定支付'+item.price+'球球币购买'+item.name+'吗？',
						success: function (res) {
						    if (res.confirm) {
								uni.showLoading({
									title: '支付中...',
									mask: true
								});
								uni.request({
									url:getApp().globalData.zuul+'shops/shop/pay',
									method:'POST',
									header:{
										'Content-Type': 'application/x-www-form-urlencoded',
										key:t.user.username,value:t.user.sessionid
									},
									data:{
										userid:t.user.id,
										shopid:item.id,
										name:item.name,
										price:item.price,
										image:item.image
									},
									success(data) {
										uni.hideLoading()
										if(data.data.status==true){
											uni.showToast({
												title:"支付成功！请去订单查看",
												duration:2000,
												icon:'none'
											})
											t.findshop();
										}else{
											uni.showToast({
												title:"错误信息："+data.data.msg,
												duration:2000,
												icon:'none'
											})
										}
										
									},
									fail() {
										uni.hideLoading()
										uni.showToast({
											title:'支付出错了！',
											duration:2000,
											icon:'none'
										})
									}
								})
								
						    } else if (res.cancel) {
						    }
						}
					})
				}
			},
			findshop(){
				let t = this;
				uni.request({
					url:getApp().globalData.zuul+'shops/shop/findall',
					method:'POST',
					header:{
						'Content-Type': 'application/x-www-form-urlencoded'
					},
					data:{},
					success(data) {
						t.shop=data.data.data;
					},
					fail() {
						uni.showToast({
							title:'获取商品失败',
							duration:2000,
							icon:'none'
						})
					}
				})
			},
			TabSelect(e) {
				this.tabCur = e.currentTarget.dataset.id;
				this.mainCur = e.currentTarget.dataset.id;
				this.verticalNavTop = (e.currentTarget.dataset.id - 1) * 50
			},
			VerticalMain(e) {
				// #ifdef MP-ALIPAY
				   return false  //支付宝小程序暂时不支持双向联动 
				// #endif
				let that = this;
				let tabHeight = 0;
				if (this.load) {
					for (let i = 0; i < this.list.length; i++) {
						let view = uni.createSelectorQuery().select("#main-" + this.list[i].id);
						view.fields({
							size: true
						}, data => {
							this.list[i].top = tabHeight;
							tabHeight = tabHeight + data.height;
							this.list[i].bottom = tabHeight;
						}).exec();
					}
					this.load = false
				}
				let scrollTop = e.detail.scrollTop + 10;
				for (let i = 0; i < this.list.length; i++) {
					if (scrollTop > this.list[i].top && scrollTop < this.list[i].bottom) {
						this.verticalNavTop = (this.list[i].id - 1) * 50
						this.tabCur = this.list[i].id
						console.log(scrollTop)
						return false
					}
				}
			}
		},
	}
</script>

<style>
	.fixed {
		position: fixed;
		z-index: 99;
	}

	.VerticalNav.nav {
		width: 200upx;
		white-space: initial;
	}

	.VerticalNav.nav .cu-item {
		width: 100%;
		text-align: center;
		background-color: #fff;
		margin: 0;
		border: none;
		height: 50px;
		position: relative;
	}

	.VerticalNav.nav .cu-item.cur {
		background-color: #f1f1f1;
	}

	.VerticalNav.nav .cu-item.cur::after {
		content: "";
		width: 8upx;
		height: 30upx;
		border-radius: 10upx 0 0 10upx;
		position: absolute;
		background-color: currentColor;
		top: 0;
		right: 0upx;
		bottom: 0;
		margin: auto;
	}

	.VerticalBox {
		display: flex;
	}

	.VerticalMain {
		background-color: #f1f1f1;
		flex: 1;
	}
</style>
