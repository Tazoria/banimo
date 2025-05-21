const getErrorMessage = (error: unknown): string => {
  if (error && typeof error === 'object' && 'response' in error) {
    const err = error as any;
    return err.response?.data?.essage ?? '서버 에러 발생';
  }
  return '알 수 없는 오류';
};

export default getErrorMessage;
