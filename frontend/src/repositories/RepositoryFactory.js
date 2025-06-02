import UserRepository from '@/repositories/UserRepository';

const repositories = {
  user: UserRepository,
};

export const RepositoryFactory = {
  get: (name) => repositories[name],
};

export default RepositoryFactory;
