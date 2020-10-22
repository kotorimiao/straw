//提交问题脚本
let  postAnswerApp=new  Vue({
    el:'#postAnswerApp',
    data:{
        message:"",
        hasError:false
    },
    methods:{
        postAnswer:function () {
            let questionId=location.search;
            if (!questionId){
                postAnswerApp.message="必须有问题ID";
                postAnswerApp.hasError=true;
                return;
            }
            //去除多余的问号
            questionId=questionId.substring(1);
            //抓取summernote内容
            let content=$('#summernote').val();
            console.log(content);
            //检查一下问题内容
            if (!content){
                postAnswerApp.message="必须有回复内容";
                postAnswerApp.hasError=true;
                return;
            }
            let  form={
                questionId:questionId,
                content:content
            };
            $.ajax({
                url:'/v1/answers',
                method:'POST',
                data:form,
                success:function (r) {
                    console.log(r);
                    if (r.code===CREATED){
                        let answer=r.data;
                        console.log(answer);
                        addDuration(answer);
                        answersApp.answers.push(answer);
                        //清空富文本编辑器
                        $("#summernote").summernote('reset');
                        postAnswerApp.message=r.message;
                        postAnswerApp.hasError=true;
                        setTimeout(function () {
                            postAnswerApp.hasError=false;
                        },1000);
                    }else{
                        postAnswerApp.message=r.message;
                        postAnswerApp.hasError=true;
                    }
                }
            });
        }
    }
});