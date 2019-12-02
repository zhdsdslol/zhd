<template>
	<view>
		<view class="cu-form-group ">
			<view class="title">视频标题</view>
			<input @input="getname" placeholder="标题(不超过十个字符)" name="input" maxlength="10"></input>
		</view>
		<view class="cu-form-group margin-top">
			<view class="title">视频分类</view>
			<picker @change="PickerChange" :value="typesindex" :range="types">
				<view class="picker">
					{{typesindex>-1?types[typesindex]:'点击选择视频分类'}}
				</view>
			</picker>
		</view>
		<view class="cu-form-group align-start">
			<view class="title">视频简介</view>
			<textarea @input="getitle" maxlength="100"  placeholder="写点什么吧!"></textarea>
		</view>
		<view class="cu-bar bg-white margin-top">
			<view class="action">
				选择封面(jpg/jpeg格式不能大于1MB)
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
		<view class="cu-form-group margin-top" @click="videoAdd">
			<view class="title">上传文件(mp4格式不能大于1MB)</view>
				<view class="picker">
					<text>{{video==null?'选择视频文件 >':('文件大小:'+size+'MB')}}</text>
				</view>
				
		</view>
		<view class="padding-xl">
			<button @click="ok" class="cu-btn block bg-blue margin-tb-sm lg">
				<text v-show="uping" class="cuIcon-loading2 cuIconfont-spin"></text>确认上传 </button>
		</view>
	</view>
</template>

<script>
	export default {
	
		data() {
			return {
				user:null,
				types:["搞笑","武侠","爱情","科幻"],
				typesindex:-1,
				imgList: [],
				video:null,
				size:null,
				name:null,
				title:null,
				uping:false,
				url:null,
				image:null,
				time:null
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
			getname(e){
				this.name=e.detail.value;
			},
			getitle(e){
				this.title=e.detail.value;
			},
			videoAdd:function () {
				var self = this;
			      uni.chooseVideo({
			        count: 1,
			        sourceType: ['camera', 'album'],
			        success: function (res) {
			          self.video = res.tempFilePath;
					self.size=parseFloat(res.size/(1024*1024)).toFixed(2);
					  
			        }
			      });
			},
			ok(){
				var self = this;
				if(!self.name||!self.title||self.typesindex==-1||self.imgList.length==0||!self.video){
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
					let up = self.imgList.concat(self.video);
					for (let i = 0; i <= up.length;i++ ) {
						uni.uploadFile({
						  url: getApp().globalData.zuul+'movies/movie/uploadVideo', //接口地址
						  header:{'key':self.user.username,'value':self.user.sessionid},
						  filePath: up[i],
						  name: 'multipartFile', 
						  success: (data) => {
							  self.uping=false;
							  let info = JSON.parse(data.data);
							  if(info.status){
								  if(i==0){
									  self.image=info.data;
								  }else{
									  self.url=info.data.path;
									  self.time=info.data.time;
									  if(self.url==null||self.time==null||self.image==null){
										  uni.showToast({
										  	icon:'none',
											title:'文件信息有误！',
											duration:"2000"
										  })
									  }else{
										  self.addmv();
									  }
								  }
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
				};
			},
			addmv(){
				uni.request({
					url: getApp().globalData.zuul+'movies/movie/addmovie',
					method:'POST',
					header: {  
					        'Content-Type': 'application/x-www-form-urlencoded'  ,
							'key':this.user.username,
							'value':this.user.sessionid
					    }, 
					data:this.$qs.stringify({	
						name:this.name,
						url:this.url,
						image:this.image,
						userid:this.user.id,
						username:this.user.loginname,
						type:this.types[this.typesindex],
						time:this.time,
						title:this.title
						}),
					success:(data)=>{
						if(data.data.status){
							uni.showToast({
								title: '保存成功！',
								duration: 3000,
								icon:'none'
							}),
							uni.redirectTo({
								url:"movieup"
							})
						}else{
							uni.showModal({
							    title: '提示',
							    content: '保存视频信息失败了！',
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
			PickerChange(e) {
				this.typesindex = e.detail.value;
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
					cancelText: '再想想',
					confirmText: '还是换张吧',
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
