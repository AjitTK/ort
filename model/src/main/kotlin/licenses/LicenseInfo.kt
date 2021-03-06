/*
 * Copyright (C) 2017-2020 HERE Europe B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */

package org.ossreviewtoolkit.model.licenses

import org.ossreviewtoolkit.model.CopyrightFinding
import org.ossreviewtoolkit.model.Identifier
import org.ossreviewtoolkit.model.LicenseFinding
import org.ossreviewtoolkit.model.PackageCurationResult
import org.ossreviewtoolkit.model.Provenance
import org.ossreviewtoolkit.spdx.SpdxExpression
import org.ossreviewtoolkit.utils.ProcessedDeclaredLicense

/**
 * This class contains all license information about a package or project.
 */
data class LicenseInfo(
    /**
     * The identifier of the package or project.
     */
    val id: Identifier,

    /**
     * Information about the declared license.
     */
    val declaredLicenseInfo: DeclaredLicenseInfo,

    /**
     * Information about the detected license.
     */
    val detectedLicenseInfo: DetectedLicenseInfo,

    /**
     * Information about the concluded license.
     */
    val concludedLicenseInfo: ConcludedLicenseInfo
)

/**
 * Information about the concluded license of a package or project.
 */
data class ConcludedLicenseInfo(
    /**
     * The concluded license, or null if no license was concluded.
     */
    val concludedLicense: SpdxExpression?,

    /**
     * The list of [PackageCurationResult]s that modified the concluded license.
     */
    val appliedCurations: List<PackageCurationResult>
)

/**
 * Information about the declared license of a package or project.
 */
data class DeclaredLicenseInfo(
    /**
     * The unmodified set of declared licenses.
     */
    val licenses: Set<String>,

    /**
     * The processed declared license.
     */
    val processed: ProcessedDeclaredLicense,

    /**
     * The list of [PackageCurationResult]s that modified the declared license.
     */
    val appliedCurations: List<PackageCurationResult>
)

/**
 * Information about the detected licenses of a package or project.
 */
data class DetectedLicenseInfo(
    /**
     * The list of all [Findings].
     */
    val findings: List<Findings>
)

/**
 * A collection of [license][licenses] and [copyright][copyrights] findings detected in the source code located at
 * [provenance].
 */
data class Findings(
    /**
     * The [Provenance] of the scanned source code.
     */
    val provenance: Provenance,

    /**
     * The set of all license findings.
     */
    val licenses: Set<LicenseFinding>,

    /**
     * The set of all copyright findings.
     */
    val copyrights: Set<CopyrightFinding>
)
