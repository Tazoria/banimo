import Repository from '@/repositories/Repository';

const resource = '/user';

export default {
  login(userInfo) {
    return Repository.post(`${resource}/login`, userInfo);
  },
  signup(userInfo) {
    return Repository.post(`${resource}/signup`, userInfo);
  },
  me() {
    return Repository.get(`${resource}/me`);
  },
};
