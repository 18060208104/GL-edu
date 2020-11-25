import request from '@/utils/request'
export default {
    //查询所有的课程
   addCourseInfo(courseinfoVo){
        return request({
            url: '/eduservice/course/addCourseInfo',      
            method: 'post',
            data:courseinfoVo
        })
    },
    //查询所有的讲师
    getListTeacher(){
       return request({
           url: '/eduservice/teacher/findAll',      
           method: 'get'           
       })
   }, 
     //根据课程id查询课程信息
   getCourseInfo(id){
        return request({
            url: `/eduservice/course/getCourseInfo/${id} `,      
            method: 'get'          
        })
    },
       //修改课程信息
    updateCourseInfo(courseinfoVo){
    return request({
        url: '/eduservice/course/updateCourseInfo',      
        method: 'post',
        data:courseinfoVo
    })
    },
     //根据课程id查询发布的最终信息
     getPublihCourseInfo(id){
        return request({
            url: `/eduservice/course/getPublishCourseInfo/${id} `,      
            method: 'get'          
        })
    },
    //课程的最终发布
    publihCourse(id){
        return request({
            url: `/eduservice/course/publishCourse/${id} `,      
            method: 'post'          
        })
    },
    getCourseListPage(current,limit,courseQuery) {
        return request({
             url: `/eduservice/course/pageCourseCondition/${current}/${limit}`,
             method: 'post',
            //teacherQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转换json进行传递到接口里面
            data: courseQuery
          })
    },
    deleteCoursebyId(id){
        return request({
            url: `/eduservice/course/${id} `,      
            method: 'delete'  
        })
    }

}   