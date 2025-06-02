<template>
  <div class="flex w-full flex-col justify-center justify-items-center items-center h-screen dark:bg-gray-900 dark:text-white">
    <div class="flex w-2/3 justify-between mb-4">
      <h1 class="text-2xl font-bold">일기 목록</h1>
      <button class="w-32 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75 transition"
              @click="createDiary()">
              일기 작성
      </button>
    </div>
    <vue-good-table
      class="w-2/3"
      :columns="columns"
      :rows="diaries" />
  </div>
</template>

<script>
import { RepositoryFactory } from '@/repositories/RepositoryFactory';
import { mapGetters } from 'vuex';

const DiaryRepository = RepositoryFactory.get('diary');

export default {
  name: 'DiaryList',
  computed: {
    ...mapGetters('user', ['userInfo']),
  },
  async created() {
    const { data } = await DiaryRepository.getDiaryList(this.userInfo.data);
    this.diaries = data.data.data;
  },
  methods: {
    createDiary() {
      this.$router.push('/diary/create');
    },
  },
  data() {
    return {
      columns: [
        {
          label: 'No',
          field: 'id',
          type: 'integer',
          width: '50px',
        },
        {
          label: '제목',
          field: 'title',
          type: 'string',
          width: '400px',
        },
        {
          label: '작성일',
          field: 'createdDate',
          type: 'date',
          dateInputFormat: 'yyyy-MM-dd',
          dateOutputFormat: 'yyyy-MM-dd',
          dateFormat: 'yyyy-MM-dd',
          width: '120px',
        },
        {
          label: '수정일',
          field: 'updatedDate',
          type: 'date',
          dateInputFormat: 'yyyy-MM-dd',
          dateOutputFormat: 'yyyy-MM-dd',
          dateFormat: 'yyyy-MM-dd',
          width: '120px',
        },
        {
          label: '즐겨찾기',
          field: 'favorites',
          type: 'boolean',
          width: '50px',
        },
      ],
      diaries: [

      ],
      loading: true,
      error: null,
    };
  },
};
</script>

<style>

</style>
