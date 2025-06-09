<template>
  <div class="max-w-2xl mx-auto mt-20 p-6 bg-white dark:bg-gray-800 rounded-lg shadow-md ">
    <!-- 제목 -->
    <h1 class="text-2xl font-bold mb-6 text-gray-900 dark:text-white text-center">
      <span v-if="mode === 'view'">다이어리 보기</span>
      <span v-else-if="mode === 'update'">다이어리 수정</span>
      <span v-else>다이어리 작성</span>
    </h1>

    <!-- 제목 입력 -->
    <div class="mb-4">
      <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">제목</label>
      <input
        v-model="diary.title"
        :readonly="mode === 'view'"
        type="text"
        class="w-full px-4 py-2 border rounded-md bg-gray-50 dark:bg-gray-700 dark:text-white dark:border-gray-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
      />
    </div>

    <!-- 내용 입력 -->
    <div class="mb-6">
      <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">내용</label>
      <textarea
        v-model="diary.content"
        :readonly="mode === 'view'"
        rows="8"
        class="w-full px-4 py-2 border rounded-md bg-gray-50 dark:bg-gray-700 dark:text-white dark:border-gray-600 focus:outline-none focus:ring-2 focus:ring-blue-500 resize-none"
      ></textarea>
    </div>

    <!-- 저장 버튼 -->
    <div class="text-right" v-if="mode !== 'view'">
      <button
        @click="createDiary()"
        class="px-6 py-2 bg-blue-600 hover:bg-blue-700 text-white font-semibold rounded-lg shadow-md transition duration-200 focus:outline-none focus:ring-2 focus:ring-blue-400"
      >
        저장
      </button>
    </div>
  </div>
</template>

<script>
import { RepositoryFactory } from '@/repositories/RepositoryFactory';
import { mapGetters } from 'vuex';
import AlertModal from '@/views/layout/modal/alertModal.vue';

const DiaryRepository = RepositoryFactory.get('diary');

export default {
  props: ['diaryId', 'detailMode'],
  computed: {
    ...mapGetters('user', ['userInfo']),
  },
  created() {
    this.diary.id = this.diaryId;
    this.mode = this.detailMode;
    this.diary.author = this.userInfo.data.username;

    if (this.diaryId) {
      this.getDiary();

      console.log('queryMode >>> ', this.mode);
      if (this.mode === 'update') {
        this.mode = 'update';
      } else {
        this.mode = 'view';
      }
    }
  },
  methods: {
    openAlertModal(title, message) {
      try {
        this.$store.dispatch('modal/openModal', {
          component: AlertModal,
          props: {
            title,
            message,
          },
        });
      } catch (e) {
        if (e.message === '중첩 모달 방지') {
          console.log('이미 모달이 열려있습니다.');
        } else {
          console.error('기타 오류: ', e);
        }
      }
    },
    async getDiary() {
      const { response } = await DiaryRepository.getDiary(this.diary.id);
      this.diary = response.data;
    },
    async createDiary() {
      let response;
      switch (this.mode) {
        case 'create':
          console.log('this.author2 > ', this.author);
          response = await DiaryRepository.createDiary(this.diary);
          if (response.status === 'success') {
            this.openAlertModal('일기 저장', '일기가 저장되었습니다.');
          } else {
            this.openAlertModal('일기 저장', '일기 저장에 실패했습니다.');
          }
          break;
        case 'update':
          response = await DiaryRepository.updateDiary(this.diary);
          if (response.status === 'success') {
            this.openAlertModal('일기 수정', '일기가 수정되었습니다.');
          } else {
            this.openAlertModal('일기 수정', '일기 수정에 실패했습니다.');
          }
          break;
        default:
          return;
      }
      this.$router.push('/diary/list');
    },
  },
  data() {
    return {
      diary: {
        id: null,
        author: '',
        title: '',
        content: '',
        isEnabled: true,
        favorite: false,
      },
      mode: 'create',
    };
  },
};
</script>
