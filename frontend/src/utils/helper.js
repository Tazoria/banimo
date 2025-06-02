export function buildQueryParams(params) {
  console.log('params >>> ', params);
  const esc = encodeURIComponent;
  return Object.keys(params)
    .map((k) => `${esc(k)}=${esc(params[k])}`)
    .join('&');
}

export default buildQueryParams;
