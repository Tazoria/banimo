<template>
  <div class="flex justify-center justify-items-center items-center h-screen">
    <vue-good-table
      :columns="columns"
      :rows="diaries" />
  </div>
</template>

<script>
export default {
  name: 'DiaryList',
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
        },
        {
          label: '내용',
          field: 'content',
          type: 'string',
        },
        {
          label: '작성일',
          field: 'createdDate',
          type: 'date',
          dateInputFormat: 'yyyy-MM-dd',
          dateOutputFormat: 'yyyy-MM-dd',
          dateFormat: 'yyyy-MM-dd',
          width: '150px',
        },
        {
          label: '수정일',
          field: 'updatedDate',
          type: 'date',
          dateInputFormat: 'yyyy-MM-dd',
          dateOutputFormat: 'yyyy-MM-dd',
          dateFormat: 'yyyy-MM-dd',
        },
        {
          label: '즐겨찾기',
          field: 'favorites',
          type: 'boolean',
          width: '50px',
        },
      ],
      diaries: [
        { id: 1, title: '첫 번째 일기', content: '오늘은 날씨가 좋았다.', createdDate: '2023-10-01', updatedDate: '2023-10-01', favorites: true },
        { id: 2, title: '두 번째 일기', content: '오늘은 친구를 만났다.', createdDate: '2023-10-02', updatedDate: '2023-10-02', favorites: false },
        { id: 3, title: '세 번째 일기', content: '오늘은 공부를 열심히 했다.', createdDate: '2023-10-03', updatedDate: '2023-10-03', favorites: true },
        { id: 4, title: '네 번째 일기', content: '오늘은 운동을 했다.', createdDate: '2023-10-04', updatedDate: '2023-10-04', favorites: false },
        { id: 5, title: '다섯 번째 일기', content: '오늘은 가족과 시간을 보냈다.', createdDate: '2023-10-05', updatedDate: '2023-10-05', favorites: true },
      ],
      loading: true,
      error: null,
    };
  },
};
</script>

<style>

</style>
