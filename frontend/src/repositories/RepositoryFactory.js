import UserRepository from '@/repositories/UserRepository';
import DiaryRepository from '@/repositories/DiaryRepository';

const repositories = {
  user: UserRepository,
  diary: DiaryRepository,
};

export const RepositoryFactory = {
  get: (name) => repositories[name],
};

export default RepositoryFactory;
