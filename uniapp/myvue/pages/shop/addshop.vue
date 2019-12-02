<template>
	<view>
		<view class="cu-form-group ">
			<view class="title">商品名称</view>
			<input @input="getname" placeholder="商品名(不超过5个字符)" name="input" maxlength="5"></input>
		</view>
		<view class="cu-form-group margin-top">
			<view class="title">商品种类</view>
			<picker @change="PickerChange" :value="typesindex" :range="types">
				<view class="picker">
					{{typesindex>-1?types[typesindex]:'点击选择商品类别'}}
				</view>
			</picker>
		</view>
		<view class="cu-form-group margin-top">
			<view class="title">商品价格</view>
			<picker @change="PickerChange1" :value="priceindex" :range="prices">
				<view class="picker">
					{{priceindex>-1?prices[priceindex]+'球球币':'商品价格'}}
				</view>
			</picker>
		</view>
		<view class="cu-form-group ">
			<view class="title">商品数量</view>
			<input @input="getnumbers" v-model="numbers" name="input" maxlength="3" type="number" disabled="true" ></input>
		</view>
		<view class="cu-bar bg-white margin-top">
			<view class="action">
				商品图片(jpg/jpeg)
			</view>
			<view class="action">
				{{imgList.length}}/1
			</view>
		</view>
		<view class="cu-form-group">
			<view class="grid col-4 grid-square flex-sub">
				<view class="bg-img" v-for="(item,index) in imgList" :key="index" @tap="ViewImage" :data-url="imgList[index]">
				 <image :src="imgList[index]" mode="aspectFill"></image>
					<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="index">
						<text class='cuIcon-close'></text>
					</view>
				</view>
				<view class="solids" @tap="ChooseImage" v-if="imgList.length<1"> <!--  -->
					<text class='cuIcon-cameraadd'></text>
				</view>
			</view>
		</view>
		<view class="padding-xl">
			<button @click="ok" class="cu-btn block bg-red margin-tb-sm lg">
				<text v-show="uping" class="cuIcon-loading2 cuIconfont-spin"></text>确认上传 </button>
		</view>
	</view>
</template>

<script>
	export default {
	
		data() {
			return {
				user:null,
				types:["零食","熬夜","非卖品"],
				typesindex:-1,
				imgList: [],
				name:null,
				numbers:999,
				uping:false,
				image:null,
				price:null,
				prices:[1,2,3,5,10],
				priceindex:-1
			}
		},
		onLoad() {
			this.islogin();
		},
		methods: {
			islogin:function(){
				let t =this;
				uni.getStorage({
					key:"user",success(e){
						t.user=e.data//这就是你想要取的
						if(t.user==null){
							uni.showModal({
							    title: '提示',
							    content: '当前未登录请登录后再试！',
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
			},
			ok(){
				var self = this;
				if(self.typesindex==-1||self.imgList.length==0||!self.price||!this.name){
					uni.showModal({
					    title: '提示',
					    content: '信息不完整！请检查后再上传！',
					    success: function (res) {
					        if (res.confirm) {
					        } else if (res.cancel) {
					        }
					    }
					});
				}else{
					self.uping=true;
						uni.uploadFile({
						  url: getApp().globalData.zuul+'movies/movie/uploadVideo', //接口地址
						  header:{'key':self.user.username,'value':self.user.sessionid},
						  filePath: self.imgList[0],
						  name: 'multipartFile', 
						  success: (data) => {
							  self.uping=false;
							  let info = JSON.parse(data.data);
							  if(info.status){
								  self.image=info.data
								  self.addmv()
							  }else{
								 uni.showModal({
								     title: '提示',
								     content: info.msg,
								     success: function (res) {
								         if (res.confirm) {
								         } else if (res.cancel) {
								         }
								     }
								 }); 
							  }
						  }
						});
				};
			},
			addmv(){
				uni.request({
					url: getApp().globalData.zuul+'shops/shop/addshop',
					method:'POST',
					header: {  
					        'Content-Type': 'application/x-www-form-urlencoded'  ,
							'key':this.user.username,
							'value':this.user.sessionid
					    }, 
					data:this.$qs.stringify({	
						name:this.name,
						image:this.image,
						type:this.types[this.typesindex],
						number:this.numbers,
						price:this.price
						}),
					success:(data)=>{
						if(data.data.status){
							uni.showToast({
								title: '保存成功！',
								duration: 3000,
								icon:'none'
							}),
							uni.redirectTo({
								url:"addshop"
							})
						}else{
							uni.showModal({
							    title: '提示',
							    content: '保存失败了！',
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
			},
			getname(e){
				this.name = e.detail.value
			},
			PickerChange(e) {
				this.typesindex = e.detail.value;
			},
			
			PickerChange1(e) {
				this.priceindex = e.detail.value;
				this.price = this.prices[this.priceindex]
			},
			ViewImage(e) {
				uni.previewImage({
					urls: this.imgList,
					current: e.currentTarget.dataset.url
				});
			},
			DelImg(e) {
				uni.showModal({
					title: '提示',
					content: '确定要删除这个封面吗？',
					cancelText: '不',
					confirmText: '换张好看的',
					success: res => {
						if (res.confirm) {
							this.imgList.splice(e.currentTarget.dataset.index, 1)
						}
					}
				})
			},
			ChooseImage() {
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: (res) => {
						if (this.imgList.length != 0) {
							this.imgList = this.imgList.concat(res.tempFilePaths)
						} else {
							this.imgList = res.tempFilePaths
						}
					}
				});
			},
		}
	
};
</script>

<style>
	
</style>
