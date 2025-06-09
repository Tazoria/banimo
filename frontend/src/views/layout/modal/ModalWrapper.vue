<template>
  <div v-if="showModal" class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50 dark:bg-opacity-70;">
    <component
      :is="modalComponent"
      v-bind="modalProps"
      @resolve="resolveModal"
      @reject="rejectModal"
      @close="closeModal"
    />
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';

export default {
  computed: {
    ...mapGetters('moddal', ['showModal', 'modalComponent', 'modalProps']),
  },
  methods: {
    ...mapActions('modal', ['closeModal', 'resolveModal', 'rejectModal']),
    handleOutsideClick(event) {
      // 모달 외부 클릭시 닫기
      const modalElement = this.$refs.modalComponent?.$el;
      if (modalElement && !modalElement.contains(event.target)) {
        this.closeModal();
      }
    },
  },
};
</script>
