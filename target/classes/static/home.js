$(document).ready(()=>{

    //网页初始化时把第一个菜单内容显示、后面两个菜单隐藏起来
    $('#m1').fadeIn()
    $('#m2').hide()
    $('#m3').hide()

    //点击退出按钮退出登录
    $('#logout').click(()=>{
        $.ajax({
            url:'http://localhost:9000/user/logout',
            method:'GET',
            success:()=>{
                window.location.href = "http://localhost:9000";
            }
        })
    })

    //点击上传菜单时触发的事件
    $('#upload').click(()=>{
        $('#m2').empty()  //把内容清空
        $('#m3').empty()
        $('#m1').fadeIn() //显示id为m1的元素
        $('#m2').hide()
        $('#m3').hide()
    })

    //点击公共图床菜单时触发的事件
    $('#public').click(()=>{
        $('#m2').empty()  //把内容清空
        $('#m3').empty()
        $('#m1').hide()
        $('#m2').fadeIn()  //显示id为m2的元素
        $('#m3').hide()
        //请求接口获取公共图片的数据
        $.ajax({
            url:'http://localhost:9000/user/getPublicImage',
            method:'GET',
            success:res=>{
                //没有数据的话插入提示元素
                if(!res.length) {
                    $('#m2').append('<span style="margin-left: 150px">该图床暂时没有公共图片</span>')
                    return
                }
                //有数据的话，遍历数组，添加元素到m2节点下
                res.forEach(item=>{
                    let image = document.createElement('img')
                    image.src = item.url
                    image.classList.add(['bed-image'])
                    image.addEventListener("click",()=>{
                        window.alert(`该图片的地址为：${item.url}`)
                    })
                    $('#m2').append(image)
                })
            }
        })
    })

    //点击私人图床菜单时触发的事件
    $('#private').click(()=>{
        $('#m2').empty() //把内容清空
        $('#m3').empty()
        $('#m1').hide()
        $('#m2').hide()
        $('#m3').fadeIn()  //显示id为m3的元素

        //请求接口获取私人图片的数据
        $.ajax({
            url:'http://localhost:9000/user/getPrivateImage',
            method:'GET',
            success:res=>{
                //没有数据的话插入提示元素
                if(!res.length) {
                    $('#m3').append('<span style="margin-left: 150px">该图床暂时没有私有图片</span>')
                    return
                }
                //有数据的话，遍历数组，添加元素到m3节点下
                res.forEach(item=>{
                    let image = document.createElement('img')
                    image.src = item.url
                    image.classList.add(['bed-image'])
                    image.addEventListener("click",()=>{
                        window.alert(`该图片的地址为：${item.url}`)
                    })
                    $('#m3').append(image)
                })
            }
        })
    })

    //点击上传按钮时触发
    $('#upload-btn').click(()=>{
        var radio = $('input[name="box"]:checked').val()  //获取单选框的值
        var files = $('input[name="file"]').prop('files')  //获取上传的文件
        if(!radio) {
            window.alert('请选择上传图片的类型')
            return
        }
        if(!files.length){
            window.alert('请选择要上传的图片')
            return
        }
        var formData = new FormData()
        formData.append("file",files[0])
        //请求接口上传图片到后端
        $.ajax({
            url: `http://localhost:9000/user/upload/${radio}`, //根据类型来上传图片
            method: 'POST',
            processData:false,
            contentType:false,
            data:formData,
            success:res=>{
                //上传成功后返回图片地址
                window.alert(`上传成功，图片地址为：${res}`)
                window.location.reload()
            },
            error:err=>{
                console.log((err))
            }
        })
    })
})