import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'



export const getDeptInfoService = (id) => {
    return request.get('/jiaowu/dept?' + id);
}

export const getTeacherInfoService = () => {
    return request.get("/jiaowu/teacher/info");
}

export const getCourseListService = () => {
    return request.get('/jiaowu/teacher/course-info');
}

export const deleteCourseService = (id) => {
    return request.delete('/jiaowu/teacher/delete/' + id);
}

export const addCourseService = (data) => {
    return request.post('/jiaowu/teacher/new', data);
}

export const updateCourseService = (data) => {
    return request.put('/jiaowu/teacher/change', data)
}