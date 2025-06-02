import Repository from '@/repositories/Repository';
import BuildQueryParams from '@/utils/helper';

const resource = '/diary';

export default {

  getDiaryList(unserInfo) {
    const query = BuildQueryParams(unserInfo);
    return Repository.get(`${resource}/list?${query}`);
  },

  getDiary(diaryId) { return Repository.post(`${resource}/detail`, diaryId); },

  createDiary(diaryData) { return Repository.post(`${resource}/create`, diaryData); },

  updateDiary(diaryId, diaryData) { return Repository.put(`${resource}/update/${diaryId}`, diaryData); },

};
