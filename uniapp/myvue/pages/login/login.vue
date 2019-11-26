<template>
	<view >
		<view class="padding flex flex-direction">
			<text class="text" >账号</text>
			</br>
			<input class="input" maxlength='10' style="background: #c3c3c4" @input="getname" type="text" placeholder="请输入您的账号"/>
		</view>
		<view class="padding flex flex-direction">
			<text class="text" >密码</text>
			</br>
			<input class="input" maxlength='10' style="background: #c3c3c4" @input="getpwd" type="password" placeholder="请输入您的密码"/>
		</view>
		</br>
		<view class="padding flex flex-direction">
			<button class="cu-btn bg-red margin-tb-sm lg" @click="login" >登录</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				username:null,
				password:null
			}
		},
		onLoad() {
		},
		onShow() {
			uni.showToast({
			    title: '请登录！',
			    duration: 3000,
				icon:'none'
			});
		},
		methods: {
			login: function  () {
				if(!this.username||!this.password){
					 uni.showToast({
					    title: '请输入账号密码！',
					    duration: 2000,
						icon:'none'
					}); 
				}else{
					uni.request({
						url: getApp().globalData.zuul+'users/user/userlogin',
						method:'POST',
						header: {  
						        'Content-Type': 'application/x-www-form-urlencoded'  
						    }, 
						data:this.$qs.stringify({	
							username:this.username,
							password:this.password,
							}),
						success:(data)=>{
							if(data.data.status=="success"){
								uni.showToast({
								    title: '登陆成功！',
								    duration: 2000
								});
								uni.setStorage({
									key:"user",data:data.data.user
									}); 
								uni.switchTab({
									url:'../user/user'
								});
							}else{
								uni.showModal({
								    title: '提示',
								    content: '登录失败请检查账号密码是否正确！',
								    success: function (res) {
								        if (res.confirm) {
								        } else if (res.cancel) {
								        }
								    }
								});
							}
						},
						fail:(err)=>{
							uni.showToast({
							    title: '服务器出错啦！',
							    duration: 2000,
								icon:'none'
							});
						}
						
					})
				}
			},
			getname:function(e){
				this.username=e.detail.value;
			},
			getpwd:function(e){
				this.password=e.detail.value;
			}
		}
	}
</script>

<style>
	.text{
		font-size: 50rpx;
		text-align:center;
	}
	.input{
		background: black;
		border-radius: 10px;
		color: white;
		font-size: 25px;
		padding: 2px;
	}
</style>
