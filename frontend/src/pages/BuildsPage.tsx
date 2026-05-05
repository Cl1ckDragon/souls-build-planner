import { useEffect, useState } from 'react'
import { getPublicBuilds } from '../api/builds'
import BuildCard from '../components/build/BuildCard'
import LoadingSpinner from '../components/common/LoadingSpinner'
import type { Build } from '../types'

export default function BuildsPage() {
  const [builds, setBuilds] = useState<Build[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    getPublicBuilds()
      .then((res) => setBuilds(res.data.content))
      .catch(() => setError('Failed to load builds.'))
      .finally(() => setLoading(false))
  }, [])

  if (loading) return <LoadingSpinner />

  if (error) return <p className="text-red-400 text-center py-16">{error}</p>

  if (builds.length === 0) {
    return (
      <p className="text-souls-ash text-center py-16">
        No builds yet. Be the first to share one!
      </p>
    )
  }

  return (
    <div>
      <h1 className="font-serif text-2xl text-souls-gold mb-6">Community Builds</h1>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {builds.map((build) => (
          <BuildCard key={build.id} build={build} />
        ))}
      </div>
    </div>
  )
}
