//初始化summernote
$(document).ready(function() {
    $('#summernote').summernote({
        height: 300,
        tabsize: 2,
        lang: 'zh-CN',
        placeholder: '请输入问题的详细描述...',
        callbacks:{
            onImageUpload:function (files) {
                //Summernote 选择了图片以后自动执行的方法
                let file = files[0];
                let form = new FormData();
                form.append("imageFile", file);
                console.log(form);
                $.ajax({
                    url: '/upload/image',
                    method: 'POST',
                    data: form,
                    contentType: false,
                    processData: false,
                    success: function (r) {
                        console.log(r);
                        if(r.code === OK){
                            let img = new Image();
                            img.src = r.message; //显示照片的URL
                            //在当前的Summernote的内容中插入照片
                            $('#summernote').summernote('insertNode', img);
                        }else {
                            alert(r.message);
                        }
                    }
                });
            }
        }
    });
});