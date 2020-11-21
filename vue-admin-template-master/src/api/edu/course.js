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
   }


}   