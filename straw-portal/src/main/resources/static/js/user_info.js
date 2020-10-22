let  userApp=new Vue({
    el:'#userApp',
    data:{
        user:[]
    },
    methods:{
        loadCurrentUser:function () {
            $.ajax({
                url:'/v1/users/me',
                method:'get',
                success:function (r) {
                    console.log(r);
                    if (r.code===OK){
                        userApp.user=r.data;
                    }else {
                        alert(r.message);
                    }
                }
            })
        }
    },
    created:function () {
        this.loadCurrentUser();
    }
});