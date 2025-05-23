const getErrorMessage = (error) => {
  if (error && typeof error === 'object' && 'response' in error) {
    const err = error;
    return err.response?.data?.message ?? '서버 에러 발생';
  }
  return '알 수 없는 오류';
};

export default getErrorMessage;
