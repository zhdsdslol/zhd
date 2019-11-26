<template>
	<view >
		<view style="background: #ff5c7c;" class="cu-bar bg-cyan search ">
			<view class="search-form radius">
				<text class="cuIcon-search"></text>
				<input v-model="!name?undefined:name" @input="getname" :adjust-position="false" type="text" placeholder="搜索视频" confirm-type="search">
					<view  v-show="name!=null&&name!=''" @click="clean" class="cu-tag round" style="background: #e7eaed;width: 50px;height: 20px;">
						<text class="cuIcon-close">
						<text style="font-size: 25rpx;" >清除</text></text>
					</view> 
				</input>
			</view> 
			<view  @click="querymovie();clear()" class="action">
				<text class="cuIcon-search"></text>
				<text>搜索</text>
			</view>
		</view>
		<view   class="padding bg-white solid-bottom " >
			 <view  v-for="(item,index) in types" :key="index" style="width: 60px;height: 30px;" :class='{ red:changeRed == index}' class='cu-tag round ' @click="findtype(index)">{{item}}</view> 
		</view>
		<view v-show="isLoad" class="cu-load bg-grey"  :class="true?'loading':'over'"></view>
		<view v-show="error" class="margin-tb-sm text-center">
			<text>QWQ出错了！↓下拉刷新</text>
		</view>
		<view v-show="have" class="margin-tb-sm text-center">
			<text>没找到数据哦</text>
		</view>
			<view style="display: flex;">
				<view class="item-container" style="width: 372rpx;">
					<view  v-for="(item,index) in movies" :key="index"  style="margin-bottom: 8px;background: #e6e6e6;border-radius: 8px;padding-xl radius shadow-warp bg-white margin-top">
						<view v-if="index==0||index%2==0" @click="towatch(item.id)">
							<image mode="widthFix" style="border-radius: 10px" :src="qqyun+item.image">
							</image>
							<view style="display: flex;justify-content:center">
								<image style="width: 15px;height: 15px;" src="../../static/icon/dianzan.ico">
									<text style="font-size: 14.5px;" >{{item.dianzan}}</text>
								</image>
								<image style="width: 15px;height: 15px;" src="../../static/icon/pl.ico">
									<text style="font-size: 14.5px;">{{item.talk}}</text>
								</image>
								<image style="width: 15px;height: 15px;" src="../../static/icon/yh.ico">
									<text style="font-size: 14.5px;">{{item.see}}</text>
								</image>
							</view>
							<text style="font-size: 15px;" >{{item.name}}</text>
							<text v-show="item.name.length<7" style="font-size: 10px;" >(时长:{{item.time}})</text>
							<view v-show="item.name.length>=7">
								<text style="font-size: 10px;" >(时长:{{item.time}})</text>
							</view>
							
						</view>
					</view> 
				</view>
				<view class="item-container2" style="width: 372rpx;">
					<view  v-for="(item,index) in movies" :key="index"   style="margin-bottom: 8px;background: #e6e6e6;border-radius: 8px;padding-xl radius shadow-warp bg-white margin-top">
						<view v-if="index!=0&&index%2!=0" @click="towatch(item.id)">
							<image mode="widthFix" style="border-radius: 10px" :src="qqyun+item.image">
							</image>
							<view style="display: flex;justify-content:center">
								<image style="width: 15px;height: 15px;" src="../../static/icon/dianzan.ico">
									<text style="font-size: 14.5px;" >{{item.dianzan}}</text>
								</image>
								<image style="width: 15px;height: 15px;" src="../../static/icon/pl.ico" space="ensp">
									<text style="font-size: 14.5px;">{{item.talk}}</text>
								</image>
								<image style="width: 15px;height: 15px;" src="../../static/icon/yh.ico">
									<text style="font-size: 14.5px;">{{item.see}}</text>
								</image>
							</view>
							<text style="font-size: 15px;" >{{item.name}}</text>
							<text v-show="item.name.length<7" style="font-size: 10px;" >(时长:{{item.time}})</text>
							<view v-show="item.name.length>=7">
								<text style="font-size: 10px;" >(时长:{{item.time}})</text>
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
				types:["全部","搞笑","武侠","爱情","科幻"],
				changeRed:0,
				movies:null,
				onec:true,
				name:null,
				type:null,
				isLoad:false,
				error:false,
				pagesize:5,
				number:1,
				totalPages:-1,
				have:false,
				qqyun:getApp().globalData.qqyun
			}
		},
		onLoad() {
			this.querymovie();
		},
		onPullDownRefresh(){
			this.onec=true;
			this.number=1;
		    this.querymovie();
			uni.stopPullDownRefresh();
		},
		  onReachBottom() {
			  console.log(this.totalPages)
			  if(this.number>=this.totalPages){
				  this.number=this.totalPages;
				  uni.showToast({
				      title: '已经到底啦！',
				      duration: 2000,
				  	icon:'none'
				  });
			  }else{ 
				this.number++;
				this.querymovie();  
			  }
		  },
		methods:{
			towatch(id){
				uni.navigateTo({
				url: 'watchtv?id='+id
				})
			},
			clean:function(){
				this.name=null;
			},
			clear:function(){
				this.onec=true;
				this.number=1;
			},
			findtype:function(index){
				this.changeRed=index;
				if(index!=0){
					this.type=this.types[index];
				}else{
					this.type=null;
				}
				this.number=1;
				this.onec=true;
				this.movies=null;
				this.querymovie()
			},
			getname:function(e){
				this.name=e.detail.value;
			},
			querymovie: function(){
				this.isLoad=true;
					uni.request({
						url: getApp().globalData.zuul+'movies/movie/querymovie',
						method:'POST',
						header: {
						        'Content-Type': 'application/x-www-form-urlencoded'  
						    }, 
						data:this.$qs.stringify({	
							type:this.type,
							name:this.name,
							pagesize:this.pagesize,
							number:this.number
							}),
						success:(data)=>{
							this.isLoad=false;
							if(data.data.status){
								if(this.onec&&data.data.data.content.length==0){
									this.have=true;
									this.movies=null;
								}else{
									this.totalPages = data.data.data.totalPages;
									this.have=false;
									if(this.onec){
										this.movies=data.data.data.content;
										this.onec=false;
									}else{
										this.movies=this.movies.concat(data.data.data.content);
									}
								}
								this.error=false;
							}else{
								this.error=true;
							}
						},
						fail:(err)=>{
							this.isLoad=false;
							this.error=true;
							uni.showToast({
							    title: '出现了意料之外的问题！',
							    duration: 2000,
								icon:'none'
							});
						}
					})
			}
		}
	}
</script>

<style>
	.red{
		color: #ff5c7c;
	}
	.head{  display: flex;}
	.item-container {
	    position: relative;
	    flex-direction: column;
	    display: flex;
	}
	 
	.item-container2 {
	    display: flex;
	    position: relative;
	    margin-left: 4px;
	    flex-direction: column;
		margin-top: -8px;
		
	}
</style>
