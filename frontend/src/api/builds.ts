import apiClient from './client'
import type { Build, Page } from '../types'

export const getPublicBuilds = (page = 0, size = 20) =>
  apiClient.get<Page<Build>>('/api/builds', { params: { page, size } })

export const getBuildBySlug = (slug: string) =>
  apiClient.get<Build>(`/api/builds/${slug}`)
