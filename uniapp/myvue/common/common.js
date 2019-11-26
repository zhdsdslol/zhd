function islogin(user){
	uni.getStorage({
		key:"user",success(e){
			user=e.data//这就是你想要取的
		}
	});
	if(user==null){
		uni.redirectTo({url:'../login/login'});
	}
} 