<template>
	<view>
		<view class="bg-img bg-mask flex align-center" style="background-image: url('https://zhdtone-1259206032.cos.ap-guangzhou.myqcloud.com/timg.jpg');height: 414upx;">
			<view class="padding-xl text-white">
				<view class="padding-xs text-xxl text-bold">
					{{this.user==null?'请先登录':this.user.loginname}}
				</view>
				<view class="padding-xs text-lg">
					{{this.title}}
				</view>
				<view class="padding-xs text-lg">
					球球币：{{this.user==null?0:this.user.qqb}}
				</view>
			</view>
			<view style="justify-content:center;display:flex;">
				<text style="color: #FFFFFF;">当前时间:{{this.gettime}}</text>
			</view>
			
		</view>
		
		<view class="cu-list menu card-menu margin-top" >
			<view class="cu-item" >
				<view class="content">
					<text class="cuIcon-upload text-green"></text>
					<text class="text-grey">我要上传视频！</text>
				</view>
				<view class="action">
					<button class="cu-btn round bg-green shadow" @click="upmovie" style="background: #0081FF;">
						<text class="cuIcon-upload" ></text> 点击上传</button>
				</view>
			</view>
		</view>
		<view class="padding flex flex-direction">
			<button class="cu-btn bg-red margin-tb-sm lg" @click="logout" >退出登录</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				gettime:null,
				title:null,
				user:null
			}
		},
		onLoad(){
			 setInterval(this.getTime,1000);
			 this.hello();
		},
		onShow() {
			this.islogin();
		},
		methods:{
			logout(){
				uni.setStorage({
					key:"user",data:null,success() {
						uni.navigateTo({url:'../login/login'});
					}
				});
			},
			islogin:function(){
				let t =this;
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
						}else{
							uni.request({
								url:getApp().globalData.zuul+'users/user/finduser',
								method:'POST',
								header:{'Content-Type': 'application/x-www-form-urlencoded',
										value:t.user.sessionid,key:t.user.username},
								data:{
									id:t.user.id
								},
								success(data) {
									uni.setStorage({
										key:'user',data:data.data.data
									})
								},
								fail() {
									uni.showToast({
										icon:'none',
										duration:2000,
										title:"获取信息出错"
									});
								}
							})
						}
					}
				});
			},
			upmovie:function(){
				uni.navigateTo({url:'../movies/movieup'});
			},
			getTime:function(){
			      let yy = new Date().getFullYear();
			      let mm = new Date().getMonth()+1;
			      let dd = new Date().getDate();
			      let hh = new Date().getHours();
			      let mf = new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes();
			      let ss = new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds();
			      this.gettime = yy+'-'+mm+'-'+dd+' '+hh+':'+mf+':'+ss;
			    },
				hello:function(){
					let hh = new Date().getHours();
					if(hh>5&&hh<11){
						this.title='早上好啊！'
					}else if(hh>=11&&hh<=14){
						this.title='要按时吃饭哦！'
					}else if(hh>=14&&hh<=18){
						this.title='今天感觉怎样？'
					}else if(hh>=18&&hh<=22){
						this.title='晚上好！'
					}else{
						this.title='早点休息吧!'
					}
				}
		}
	}
</script>

<style>
</style>
