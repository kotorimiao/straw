Vue.component('v-select', VueSelect.VueSelect);
let createQuestionApp=new  Vue({
    el:'#createQuestionApp',
    data:{
        title:'',
        tags:[],
        selectedTags:[],
        teachers:[],
        selectedTeachers:[],
    },
    methods:{
        loadTags:function () {
            console.log("loadTags");
            $.ajax({
                url:'/v1/tags',
                method:'GET',
                success:function (r) {
                    console.log(r);
                    if (r.code===OK){
                        let list=r.data;
                        let  tags=[];
                        for (let i = 0; i < list.length; i++) {
                            tags.push(list[i].name);
                        }
                        createQuestionApp.tags=tags;
                    }
                }
            });
        },
        loadTeachers:function () {
            console.log("开始加载讲师列表");
            $.ajax({
                url: '/v1/users/masters',
                method:'GET',
                success:function (r) {
                    console.log(r);
                    if(r.code===OK){
                        let list=r.data;
                        let teachers=[];
                        for (let i = 0; i < list.length; i++) {
                            teachers.push(list[i].nickname);
                        }
                        createQuestionApp.teachers=teachers;
                    }else {
                        console.log("失败");
                        console.log(r.message);
                    }
                }
            });
        },
        createQuestion:function () {
            //获得表单数据
            //获得富文本编辑器中的正文内容
            let  content=$('#summernote').val();
            let  data={
                title:this.title,
                tagNames:this.selectedTags,
                teacherNicknames:this.selectedTeachers,
                content:content
            };
            console.log(data);
            $.ajax({
                url:'/v1/questions',
                method:'POST',
                traditional: true,//表示采用传统方式编码  不然数组发送多了[]  springmvc解析会出问题
                data: data,
                success:function (r) {
                    console.log(r);
                    if (r.code===CREATED){
                        console.log(r.message);
                        location.href='../index.html'
                    }else{
                        console.log(r.message);
                        alert(r.message);
                    }
                }
            });
        }
    },
    created:function () {
        this.loadTags();
        this.loadTeachers();
    }
});