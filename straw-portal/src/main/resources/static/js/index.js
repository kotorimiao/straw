
//用户所有问题的视图模型
let questionsApp=new  Vue({
    el:'#questionsApp',
    data:{
        questions:[],
        pageInfo:{}
    },
    methods:{
        loadQuestions:function(pageNum){
            if(!pageNum){
                pageNum=1;
            }
            $.ajax({
                url:'/v1/questions/my',
                method:"GET",
                data:{
                    pageNum:pageNum
                },
                success:function(r){
                    console.log("成功加载数据");
                    console.log(r);
                    if(r.code===OK){
                        questionsApp.questions=r.data.list;
                        questionsApp.pageInfo=r.data;
                        //立即执行更新时间的方法
                        questionsApp.updateDuration();
                        //执行更换图片的逻辑
                        questionsApp.updateTagImage();

                    }
                }
            });

        },
        //显示图片的逻辑
        updateTagImage:function(){
            let questions=this.questions;
            for (let i = 0; i <questions.length; i++) {
                let  tags=questions[i].tags;
                if(tags){
                    let  tagImage="/img/tags/"+tags[0].id+".jpg";
                    console.log("tagImage"+tagImage);
                    questions[i].tagImage=tagImage;
                }
            }
        },
        //计算时间显示的方法
        updateDuration:function () {
            let   questions=this.questions;
            for (let i=0;i<questions.length;i++){
                addDuration(questions[i]);
            }

        }
    },
    created:function () {
        console.log("执行了方法");
        this.loadQuestions(1);
    }
});