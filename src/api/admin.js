import request from '@/utils/request.js'

export const studentListService = (data) => {
    const params = new URLSearchParams()
    for (let key in data) {
        params.append(key, data[key]);
    }
    // console.log(params)
    return request.get('/admin/student/page?' + params);
}

export const addStudentService = (data) => {
    return request.post('/admin/student', data)
}

export const updateStudentService = (data) => {
    return request.put('/admin/student', data)
}

export const deleteStudentService = (id) => {
    return request.delete('/admin/student/' + id)
}

export const getMajorListService = () => {
    return request.get('/jiaowu/major');
}

export const getDeptListService = () => {
    return request.get('/jiaowu/dept');
}