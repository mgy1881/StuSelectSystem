import request from '@/utils/request.js'

export const loginService = (data) => {
    return request.post("/login", data);
}