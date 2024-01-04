import request from '@/utils/request.js'

// 学生管理
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

//教师管理
export const getTeacherListService = () => {
    return request.get('/admin/teacher');
}

export const addTeacherService = (data) => {
    return request.post('/admin/teacher', data)
}

export const updateTeacherService = (data) => {
    return request.put('/admin/teacher', data)
}

export const deleteTeacherService = (id) => {
    return request.delete('/admin/teacher/' + id)
}
export const teacherListService = (data) => {
    const params = new URLSearchParams()
    for (let key in data) {
        params.append(key, data[key]);
    }
    // console.log(params)
    return request.get('/admin/teacher/page?' + params);
}

// 课程管理
export const courseListService = (data) => {
    const params = new URLSearchParams()
    for (let key in data) {
        params.append(key, data[key]);
    }
    // console.log(params)
    return request.get('/admin/course/page?' + params);
}
export const addCourseService = (data) => {
    return request.post('/admin/course', data)
}

export const updateCourseService = (data) => {
    return request.put('/admin/course', data)
}

export const deleteCourseService = (id) => {
    return request.delete('/admin/course/' + id)
}

export const getStudentListByCourseIdService = (id) => {
    return request.get('/admin/cs/' + id)
}
export const getStudentListByStudentIdService = (id) => {
    return request.get('/admin/student/' + id)
}

// 院系管理
export const getDeptListService = () => {
    return request.get('/jiaowu/dept');
}

export const updateDeptService = (data) => {
    return request.put('/admin/dept', data);
}

export const addDeptService = (data) => {
    return request.post('/admin/dept', data);
}

export const deleteDeptService = (id) => {
    return request.delete('/admin/dept/' + id);
}

// 专业管理
export const getMajorListService = () => {
    return request.get('/jiaowu/major');
}

export const updateMajorService = (data) => {
    return request.put('/admin/major', data);
}

export const addMajorService = (data) => {
    return request.post('/admin/major', data);
}

export const deleteMajorService = (id) => {
    return request.delete('/admin/major/' + id);
}