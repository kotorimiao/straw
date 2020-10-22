let questionApp=new Vue({
    el:'#questionApp',
    data:{
        question:{}
    },
    methods:{
        loadQuestion:function () {
            let  questionId=location.search;
            if(!questionId){
                alert('必须指定问题的ID');
                return;
            }
            //去除查询参数中的问号
            questionId=questionId.substring(1);
            $.ajax({
                url:'/v1/questions/'+questionId,
                method:'GET',
                success:function (r) {
                    console.log(r);
                    if(r.code===OK){
                        questionApp.question = r.data;
                        //持续时间更新
                        addDuration(questionApp.question);
                    }else{
                        alert(r.message);
                    }

                }
            });
        },
    },
    created:function () {
        console.log("loadQuestion执行了");
        this.loadQuestion();
    }
});
//提交答案功能

//vue视图模型
//
let  answersApp=new  Vue({
    el:'#answersApp',
    data:{
        answers:[]
    },
    methods:{
        loadAnswers:function () {
            //拿到当前问题的id
            let questionId=location.search;
            if (!questionId){
                alert("必须要有问题id");
                return;
            }
            questionId=questionId.substring(1);
            $.ajax({
                //  /v1/answers/question/{id}
                url:'/v1/answers/question/'+questionId,
                method:'GET',
                success:function (r) {
                    console.log(r);
                    if (r.code===OK){
                        answersApp.answers=r.data;
                        answersApp.updateDuration();
                    }else {
                        alert("查询答案失败");
                    }
                }
            });
        },
        updateDuration:function () {
            for (let i = 0; i < this.answers.length; i++) {
                addDuration(this.answers[i]);
            }
        },
        postComment:function (answerId) {
            if (!answerId){
                return;
            }
            //找到表单中的文本域
            let  textarea=$('#addComment'+answerId+' textarea');
            let  content=textarea.val();
            if (!content){
                return;
            }
            let form={
                answerId:answerId,
                content:content
            };
            $.ajax({
                url:'/v1/comments',
                method:'post',
                data:form,
                success:function (r) {
                    console.log(r);
                    if (r.code===CREATED){
                        let comment=r.data;
                        //找到当前问题的全部评论列表 将新评论插入到列表最后
                        let   answers=answersApp.answers;
                        for (let i = 0; i <answers.length; i++) {
                            if (answers[i].id===answerId){
                                answers[i].comments.push(comment);
                                break;
                            }
                        }
                        textarea.val("");
                        //alert(r.message);

                    }else{
                        alert(r.message);
                    }
                }
            });
        },
        removeComment:function (commentId,index,comments) {
            console.log("commentId:"+commentId);
            if(!commentId){
                return;
            };
            $.ajax({
                url:'/v1/comments/'+commentId+'/delete',
                method:'GET',
                success: function (r) {
                    console.log(r);
                    if (r.code===GONE){
                       // alert(r.message);
                        //从评论数组中删除已经被删除的评论
                        comments.splice(index,1);
                    }else{
                        alert(r.message);
                    }
                }
            });

        },
        updateComment:function (commentId,answerId,index,comments) {
            if(!commentId){
                return;
            }
            if(!answerId){
                return;
            }
            let  textarea=$('#editComment'+commentId+' textarea');
            let  content=textarea.val();
            if (!content)return;
            let form={
                answerId:answerId,
                content:content
            };
            $.ajax({
                url:'/v1/comments/'+commentId+'/update',
                method:'post',
                data:form,
                success:function (r) {
                    console.log(r);
                    if (r.code===OK){
                        Vue.set(comments,index,r.data);
                        //alert(r.message);
                        $("#editComment"+commentId).collapse("hide");
                    }else{
                        alert(r.message);
                    }
                }
            });
        },
        answerSolved:function (answerId,answer) {
            if (answer.acceptStatus===1){
                alert("已经解决");
                return;
            }
            $.ajax({
                url:'/v1/answers/'+answerId+'/solved',
                method:'GET',
                success:function (r) {
                    console.log(r);
                    if (r.code===ACCEPTED){
                        alert("解决了！");
                        answer.acceptStatus=1;
                    }else{
                        alert(r.message);
                    }

                }
            });
        }
    },
    //生命周期管理函数
    created:function () {
        this.loadAnswers();
    }
});

