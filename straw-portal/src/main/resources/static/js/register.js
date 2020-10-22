//标准vue的视图模型
let  app=new  Vue({
    el:'#app',
    data:{
        inviteCode:'',
        phone:'',
        nickname:'',
        password:'',
        confirm:'',
        message:'',
        hasError:false  //默认false
    },
    methods:{
        register:function(){
            console.log("提交submit！");
            let data={
                inviteCode: this.inviteCode,
                phone: this.phone,
                nickname: this.nickname,
                password: this.password,
                confirm: this.confirm
            };
            console.log(data);
            //验证密码是否一致
            if(data.password!==data.confirm){
                this.message='确认密码不一致';
                this.hasError=true;
                return;
            }
            let _this=this;  //因为后续再ajax中使用Vue当前对象  直接写this代表ajax对象
            $.ajax({
                url:"/register",
                method:"post",
                data: data,
                success:function(r){
                    console.log("接收到的数据："+r);
                    if(r.code==CREATED){
                        console.log("注册成功："+r.message);
                        _this.hasError=false;//注册成功不显示
                        //注册成功后跳转到登录页  携带注册页面信息
                        location.href='/login.html?register';
                    }else{
                        console.log(r.message);
                        _this.hasError=true;//注册失败显示信息内容  并且显示失败信息
                        _this.message=r.message;
                    }
                }
            });
        }
    }
});