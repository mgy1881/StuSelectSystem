import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

export const getMajorInfoService = (id) => {
    return request.get('/jiaowu/major?' + id);
}

export const getDeptInfoService = (id) => {
    return request.get('/jiaowu/dept?' + id);
}

export const getStudentInfoService = () => {
    return request.get("/jiaowu/student/info");
}

export const getCourseInfoService = (data) => {
    const params = new URLSearchParams()
    for (let key in data) {
        params.append(key, data[key]);
    }
    // console.log(params)
    return request.get('/jiaowu/student/course?' + params);
}

export const selectCourseService = (data) => {
    return request.post('/jiaowu/student/course', data)
}

export const getSelectedCourseService = () => {
    return request.get('/jiaowu/student/selected-courses')
}

export const dropCourseService = (id) => {
    return request.delete('/jiaowu/student/course/' + id);
}